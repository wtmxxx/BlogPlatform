package com.wotemo.service;

import com.wotemo.pojo.UserArticle;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserArticleService {
    List<UserArticle> getArticles(Integer skip, Integer num);

    String setArticle(HttpServletRequest request, String userId, String title, String content) throws Exception;

    UserArticle getArticle(String id);

    UserArticle updateArticle(HttpServletRequest request, String id, String title, String content, Integer dislike) throws Exception;

    boolean deleteArticle(HttpServletRequest request, String id) throws Exception;

    List<UserArticle> getArticlesByUserId(String userId, Integer skip, Integer num);

    void CancelledArticle(String articleId);
}
