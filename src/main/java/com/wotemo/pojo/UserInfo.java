package com.wotemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String userId;
    private String nickname;
    private String avatar;
    private LocalDate birthday;
    private String address;
    private String profile;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 省略 getter 和 setter 方法
}
