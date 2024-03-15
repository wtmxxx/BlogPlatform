package com.wotemo.service.impl;

import com.wotemo.mapper.UserInfoMapper;
import com.wotemo.pojo.UserInfo;
import com.wotemo.service.IdentityService;
import com.wotemo.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoMapper userInfoMapper;
    private final IdentityService identityService;
    @Autowired
    public UserInfoServiceImpl(UserInfoMapper userInfoMapper, IdentityService identityService){
        this.userInfoMapper = userInfoMapper;
        this.identityService = identityService;
    }

    // 获取用户信息 ID
    @Override
    public UserInfo getUserInfo(String userId) {
        return userInfoMapper.getUserInfo(userId);
    }

    @Override
    public UserInfo updateUserInfo(HttpServletRequest request, String userId, String nickname, String avatar, LocalDate birthday, String profile) throws Exception {
        if (identityService.check(request, userId, IdentityService.ById)) {
            userInfoMapper.updateUserInfo(userId, nickname, avatar, birthday, profile);
            return userInfoMapper.getUserInfo(userId);
        } else {
            return null;
        }
    }

    @Override
    public void setUserInfo(String userId, String nickname, String avatar, LocalDate birthday, String address, String profile) {
        userInfoMapper.setUserInfo(userId, nickname, avatar, birthday, address, profile);
//        return getUserInfo(userId);
    }

    @Override
    public void deleteUserInfo(String userId) throws Exception {
        userInfoMapper.deleteUserInfo(userId);
    }
}
