package com.wotemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserArticle {
    private String id;
    private String author;
    private String title;
    private String content;
    private Integer dislike;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 省略 getter 和 setter 方法
}
