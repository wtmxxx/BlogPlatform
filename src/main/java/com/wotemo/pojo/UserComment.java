package com.wotemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserComment {
    private String id;
    private String articleId;
    private String userId;
    private String content;
    private String parentId;
    private LocalDateTime createTime;

    // 省略 getter 和 setter 方法
}
