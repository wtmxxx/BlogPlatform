package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.service.UserCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
