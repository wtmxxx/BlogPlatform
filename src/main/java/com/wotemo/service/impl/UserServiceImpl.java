package com.wotemo.service.impl;

import com.wotemo.mapper.UserMapper;
import com.wotemo.pojo.User;
import com.wotemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
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

    @Override
    public User updateUser(String username, String email) throws Exception {
        userMapper.updateUser(username, email);
        return getUser(null, username, null);
    }

}
