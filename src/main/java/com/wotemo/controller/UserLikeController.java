package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.service.UserLikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserLikeController {
    private final UserLikeService userLikeService;
    @Autowired
    public UserLikeController(UserLikeService userLikeService){
        this.userLikeService = userLikeService;
    }

    @GetMapping("/get/likes")
    public Result getLikes(String obj, String id, @RequestParam(defaultValue = "0") Integer skip, @RequestParam(defaultValue = "10") Integer num) throws Exception {
        log.info("获取最新点赞");
        return Result.success(userLikeService.getLikes(obj, id, skip, num));
    }

    @GetMapping("/get/like")
    public Result getLike(String userId, String articleId) throws Exception {
        log.info("获取用户是否对文章点赞");
        return Result.success(userLikeService.getLike(userId, articleId));
    }

    @GetMapping("/get/likes/count")
    public Result getLikes(String obj, String id) throws Exception {
        log.info("获取点赞总数");
        return Result.success(userLikeService.getLikesCount(obj, id));
    }

    @PostMapping("/set/like")
    public Result setLikes(HttpServletRequest request, String userId, String articleId) throws Exception {
        log.info("对文章点赞");
        boolean isSetLike = userLikeService.setUserLike(request, userId, articleId);
        if (isSetLike) {
            return Result.success(true);
        } else {
            return Result.error("身份验证失败");
        }
    }
}
