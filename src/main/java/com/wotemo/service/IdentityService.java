package com.wotemo.service;

import com.wotemo.pojo.User;
import jakarta.servlet.http.HttpServletRequest;

public interface IdentityService {
    static final String ById = "ById";
    static final String ByUsername = "ByUsername";
    static final String ByEmail = "ByEmail";

    // 获取用户 ID或邮箱
    User getUserById(String id) throws Exception;

    public boolean check(HttpServletRequest request, String identity, String way) throws Exception;


}
