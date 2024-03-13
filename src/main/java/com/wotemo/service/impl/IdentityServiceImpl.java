package com.wotemo.service.impl;

import com.wotemo.mapper.UserMapper;
import com.wotemo.pojo.User;
import com.wotemo.service.IdentityService;
import com.wotemo.service.UserService;
import com.wotemo.utils.JwtUtils;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class IdentityServiceImpl implements IdentityService {
    private final UserMapper userMapper;
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Autowired
    public IdentityServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 获取用户 ID或邮箱
    @Override
    public User getUserById(String id) throws Exception {
        if (id != null) {
            return userMapper.getUser(id, null, null);
        } else {
            throw new Exception("缺少ID");
        }

    }

    @Override
    public boolean check(HttpServletRequest request, String identity, String way) throws Exception {
        log.info("正在通过 {} 验证 {} 的身份", way, identity);

        JwtUtils jwtUtils = new JwtUtils();
        jwtUtils.setSECRET_KEY(SECRET_KEY);
        Jwt<Header, Map<String, Object>> parsedJwt = jwtUtils.parseJWT(request.getHeader("token"));
        Map<String, Object> payload = parsedJwt.getPayload();
        String id = (String) payload.get("sub");

        User user = getUserById(id);

        // 检验是否为管理员
        if (user.getIsAdministrator() == 1) {
            return true;
        }

        // 检验是否为本人操作
        if (Objects.equals(way, ById)) {
            return identity.equals(user.getId());
        } else if (Objects.equals(way, ByUsername)) {
            return identity.equals(user.getUsername());
        } else if (Objects.equals(way, ByEmail)) {
            return identity.equals(user.getEmail());
        } else {
            return false;
        }
    }
}
