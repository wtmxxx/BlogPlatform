package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.pojo.UserArticle;
import com.wotemo.service.UserArticleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class UserArticleController {
    private final UserArticleService userArticleService;
    @Autowired
    public UserArticleController(UserArticleService userArticleService){
        this.userArticleService = userArticleService;
    }

    @GetMapping("/get/articles")
    public Result getArticles(Integer skip, Integer num){
        log.info("获取最新文章");
        return Result.success(userArticleService.getArticles(skip, num));
    }

    @GetMapping("/get/article")
    public Result getArticle(String id){
        log.info("根据ID获取文章");
        return Result.success(userArticleService.getArticle(id));
    }

    @PostMapping("/set/article")
    public Result setArticle(HttpServletRequest request,
                             @RequestParam String userId,
                             @RequestParam String title,
                             @RequestParam String content) throws Exception {
        log.info("发表一篇文章");
        String id = userArticleService.setArticle(request, userId, title, content);
        return Result.success(userArticleService.getArticle(id));
    }

    @PostMapping("/update/article")
    public Result updateArticle(HttpServletRequest request,
                                @RequestParam String id,
                                String title,
                                String content,
                                Integer dislike) throws Exception {
        log.info("修改文章");
        UserArticle userArticle = userArticleService.updateArticle(request, id, title, content, dislike);
        if (userArticle != null) {
            return Result.success(userArticle);
        } else {
            return Result.error("身份验证失败");
        }
    }
}
