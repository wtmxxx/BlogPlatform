package com.wotemo.service;

import com.wotemo.pojo.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;

public interface UserInfoService {
    UserInfo getUserInfo(String userId);

    UserInfo updateUserInfo(HttpServletRequest request, String id, String nickname, String avatar, LocalDate birthday, String profile) throws Exception;

    void setUserInfo(String userId, String nickname, String avatar, LocalDate birthday, String address, String profile);
}
