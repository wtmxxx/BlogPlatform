package com.wotemo.service.impl;

import com.wotemo.mapper.UserPasswordMapper;
import com.wotemo.pojo.UserPassword;
import com.wotemo.service.CheckCodeService;
import com.wotemo.service.UserPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserPasswordServiceImpl implements UserPasswordService {
    private final UserPasswordMapper userPasswordMapper;
    private final CheckCodeService checkCodeService;
    @Autowired
    public UserPasswordServiceImpl(UserPasswordMapper userPasswordMapper, CheckCodeService checkCodeService){
        this.userPasswordMapper = userPasswordMapper;
        this.checkCodeService = checkCodeService;
    }

    @Override
    public UserPassword getPassword(String username, String email) {
        log.info("Service [getPassword] 用户名为: {}, 邮箱为: {}, 尝试获取密码", username, email);
        return userPasswordMapper.getPassword(username, email);
    }

    @Override
    public boolean updatePasswordByUsername(String username, String oldPassword, String newPassword) {
        log.info("Service [updatePasswordByUsername] 用户名为: {}, 尝试修改密码", username);
        if (getPassword(username, null).getPassword().equals(oldPassword)) {
            return userPasswordMapper.updatePasswordByUsername(username, newPassword);
        } else {
            return false;
        }

    }

    @Override
    public boolean updatePasswordByEmail(String email, String password, String codeId, String code) {
        log.info("Service [updatePasswordByEmail] 邮箱为: {}, 尝试修改密码", email);
        if (checkCodeService.checkCode(codeId, code, email)) {
            return userPasswordMapper.updatePasswordByEmail(email, password);
        } else {
            return false;
        }
    }

    @Override
    public void updateUsernameEmail(String userId, String username, String email) {
        userPasswordMapper.updateUsernameEmail(userId, username, email);
    }


    @Override
    public void setPassword(String userId, String username, String email, String password) {
        log.info("Service [setPassword] 用户名为: {}, 尝试设置密码", username);
        userPasswordMapper.setPassword(userId, username, email, password);
    }

    @Override
    public void deletePassword(String userId) {
        log.info("删除用户密码");
        userPasswordMapper.deletePassword(userId);
    }
}
