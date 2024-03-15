package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.pojo.UserInfo;
import com.wotemo.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
public class UserInfoController {
    private final UserInfoService userInfoService;
    @Autowired
    public UserInfoController(UserInfoService userInfoService){
        this.userInfoService = userInfoService;
    }

    @GetMapping("/get/userinfo")
    public Result getUserInfo(String id){
        log.info("获取用户信息");
        return Result.success(userInfoService.getUserInfo(id));
    }

    @PostMapping("/update/userinfo")
    public Result updateAddress(HttpServletRequest request,
                                @RequestParam String userId,
                                String nickname,
                                String avatar,
                                LocalDate birthday,
                                String profile) throws Exception {
        log.info("更新用户信息");
        UserInfo userInfo = userInfoService.updateUserInfo(request, userId, nickname, avatar, birthday, profile);
        if (userInfo != null) {
            return Result.success(userInfo);
        } else {
            return Result.error("身份验证失败");
        }

    }

}
