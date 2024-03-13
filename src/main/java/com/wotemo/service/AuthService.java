package com.wotemo.service;


import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AuthService {
    String loginByPassword(String username, String email, String password) throws Exception;
    String loginByCheckCode(String email, String codeId, String code) throws Exception;

    void logout(String jwt);

    String register(String username, String email, String password,
                    String nickname, String avatar, LocalDate birthday, String profile,
                    String codeId, String code,
                    String province, String city, String full_address) throws Exception;
}
