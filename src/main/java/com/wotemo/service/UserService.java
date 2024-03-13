package com.wotemo.service;

import com.wotemo.pojo.User;


public interface UserService {
    User getUser(String id, String username, String email) throws Exception;

    User setUser(String username, String email) throws Exception;

    User updateUser(String username, String email) throws Exception;
}
