package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.pojo.UserComment;
import com.wotemo.service.UserCommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserCommentController {
    private final UserCommentService userCommentService;
    @Autowired
    public UserCommentController(UserCommentService userCommentService){
        this.userCommentService = userCommentService;
    }

    @GetMapping("/get/comments")
    public Result getComments(String obj, @RequestParam String id, Integer skip, Integer num) throws Exception {
        log.info("获取最新评论");
        return Result.success(userCommentService.getComments(obj, id, skip, num));
    }

    @GetMapping("/get/comment")
    public Result getComment(String id){
        log.info("根据ID获取评论");
        return Result.success(userCommentService.getComment(id));
    }

    @PostMapping("/set/comment")
    public Result setComment(HttpServletRequest request,
                             @RequestParam String articleId,
                             @RequestParam String userId,
                             @RequestParam String content,
                             String parentId) throws Exception {
        log.info("发表一个评论");
        String id = userCommentService.setComment(request, articleId, userId, content, parentId);
        UserComment userComment = userCommentService.getComment(id);
        System.out.println(userComment);
        if (userComment != null) {
            return Result.success(userComment);
        } else {
            return Result.error("身份验证失败");
        }
    }

    @DeleteMapping("/delete/comment")
    public Result deleteComment(HttpServletRequest request, @RequestParam String id) throws Exception {
        log.info("删除一个评论");
        if (userCommentService.deleteComment(request, id)) {
            return Result.success();
        } else {
            return Result.error("身份验证失败");
        }
    }
}
