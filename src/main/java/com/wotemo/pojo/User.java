package com.wotemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String username;
    private String email;
    private String userInfo;
    private short isAdministrator;
    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    // 省略 getter 和 setter 方法
}
