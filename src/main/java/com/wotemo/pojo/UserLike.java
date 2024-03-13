package com.wotemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLike {
    private String userId;
    private String articleId;
    private LocalDateTime createTime;

    // 省略 getter 和 setter 方法
}
