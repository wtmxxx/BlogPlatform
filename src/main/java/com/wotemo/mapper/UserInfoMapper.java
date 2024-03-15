package com.wotemo.mapper;

import com.wotemo.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper
public interface UserInfoMapper {
    // 获取用户信息 ID
    // @Select UserInfoMapper.xml
    public UserInfo getUserInfo(String userId);

    public void updateUserInfo(String userId, String nickname, String avatar, LocalDate birthday, String profile);

    public void setUserInfo(String userId, String nickname, String avatar, LocalDate birthday, String address, String profile);

    void deleteUserInfo(String userId);
}
