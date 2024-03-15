package com.wotemo.service.impl;

import com.wotemo.service.CheckCodeService;
import com.wotemo.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CheckCodeServiceImpl implements CheckCodeService {
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailService emailService;
    @Autowired
    public CheckCodeServiceImpl(RedisTemplate redisTemplate, EmailService emailService) {
        this.redisTemplate = redisTemplate;
        this.emailService = emailService;
    }

    @Override
    public void addCode(String id, String code, long timeout) {
        redisTemplate.opsForValue().set(id, code, timeout, TimeUnit.SECONDS);
    }

    @Override
    public String addRandomCode(String to, long timeout) throws MessagingException {
        log.info("发送验证码到: {}", to);
        String uuid = String.valueOf(UUID.randomUUID());
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // 生成0-9的随机数
            sb.append(digit);
        }
        String code = sb + "&" + to;
        String subject = "BlogPlatform邮箱验证";
        String text = "您的验证码为：" + sb + "\n注意：此操作可能会修改您的密码、登录邮箱或绑定手机。客服人员不会向您索要此校验码，请勿泄漏，如非本人操作，请忽略此邮件。";
        emailService.SendEmail(to, subject, text);
        addCode(uuid, code, timeout);
        return uuid;
    }


    @Override
    public String getCode(String id){
        log.info("查询ID:{}的验证码", id);
        return redisTemplate.opsForValue().get(id);
    }

    @Override
    public boolean checkCode(String id, String code, String email) {
        log.info("检验ID:{}的验证码是否为:{}", id, code);
        code += "&" + email;
        String realCode = redisTemplate.opsForValue().get(id);
        deleteCode(id);
        return Objects.equals(realCode, code);
    }

    @Override
    public void deleteCode(String id) {
        redisTemplate.delete(id);
    }
}
