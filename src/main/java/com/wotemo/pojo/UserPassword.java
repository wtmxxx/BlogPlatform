package com.wotemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPassword {
    private String userId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 省略 getter 和 setter 方法
}
