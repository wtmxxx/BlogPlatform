package com.wotemo.service;

import com.wotemo.pojo.UserPassword;

public interface UserPasswordService {
    UserPassword getPassword(String username, String email);

    boolean updatePasswordByUsername(String username, String oldPassword, String newPassword);
    boolean updatePasswordByEmail(String email, String password, String codeId, String code);

    void setPassword(String userId, String username, String email, String password);
}
