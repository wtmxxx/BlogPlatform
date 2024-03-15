package com.wotemo.service.impl;

import com.wotemo.mapper.UserMapper;
import com.wotemo.pojo.User;
import com.wotemo.service.CheckCodeService;
import com.wotemo.service.IdentityService;
import com.wotemo.service.UserPasswordService;
import com.wotemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final IdentityService identityService;
    private final CheckCodeService checkCodeService;
    private final UserPasswordService userPasswordService;
    @Autowired
    public UserServiceImpl(UserMapper userMapper, IdentityService identityService, CheckCodeService checkCodeService, UserPasswordService userPasswordService) {
        this.userMapper = userMapper;
        this.identityService = identityService;
        this.checkCodeService = checkCodeService;
        this.userPasswordService = userPasswordService;
    }

    // 获取用户 ID或邮箱
    @Override
    public User getUser(String id, String username, String email) throws Exception {
        if (
                (id != null && username == null && email == null) ||
                (id == null && username != null && email == null) ||
                (id == null && username == null && email != null))
        {
            return userMapper.getUser(id, username, email);
        } else {
            throw new Exception("没有或有且超过一个数据被请求,请仅传入一个值");
        }

    }

    @Override
    public User setUser(String username, String email) throws Exception {
        String uuid = String.valueOf(UUID.randomUUID());
        userMapper.setUser(uuid, username, email);
        return getUser(uuid, null, null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User updateUser(HttpServletRequest request, String id, String username, String email, String codeId, String code) throws Exception {
        if (checkCodeService.checkCode(codeId, code, userMapper.getUser(id, null, null).getEmail())) {
            if (identityService.check(request, id, IdentityService.ById)) {
                userMapper.updateUser(id, username, email);
                userPasswordService.updateUsernameEmail(id, username, email);
                return userMapper.getUser(id, null, null);
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    @Override
    public void deleteUser(String userId) throws Exception {
            userMapper.deleteUser(userId);
    }

    @Override
    public boolean setAdministrator(HttpServletRequest request, String id) throws Exception {
        if (identityService.check(request, id, IdentityService.ById)) {
            if (userMapper.getUser(id, null, null).getIsAdministrator() == 0) {
                userMapper.setAdministrator(id, (short) 1);
            } else {
                userMapper.setAdministrator(id, (short) 0);
            }
            return true;
        } else {
            return false;
        }
    }

}
