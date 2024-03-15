package com.wotemo.mapper;

import com.wotemo.pojo.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    // 获取用户数据 ID或用户名或邮箱皆可
    // @Select UserMapper.xml
    public User getUser(String id, String username, String email);

    public void setUser(String id, String username, String email);

    public void updateUser(String id, String username, String email);

    void deleteUser(String userId);

    void setAdministrator(String id, short isAdministrator);
}
