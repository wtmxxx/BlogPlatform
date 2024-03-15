package com.wotemo.service;

import com.wotemo.pojo.User;
import jakarta.servlet.http.HttpServletRequest;


public interface UserService {
    User getUser(String id, String username, String email) throws Exception;

    User setUser(String username, String email) throws Exception;

    User updateUser(HttpServletRequest request, String id, String username, String email, String codeId, String code) throws Exception;

    void deleteUser(String id) throws Exception;

    boolean setAdministrator(HttpServletRequest request, String id) throws Exception;
}
