package com.wotemo.mapper;

import com.wotemo.pojo.UserPassword;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPasswordMapper {
    public UserPassword getPassword(String username, String email);

    public void setPassword(String userId, String username, String email, String password);

    boolean updatePasswordByUsername(String username, String newPassword);

    boolean updatePasswordByEmail(String email, String password);
}
