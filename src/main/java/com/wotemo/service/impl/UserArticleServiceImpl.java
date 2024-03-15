package com.wotemo.service.impl;

import com.wotemo.mapper.UserArticleMapper;
import com.wotemo.pojo.UserArticle;
import com.wotemo.service.IdentityService;
import com.wotemo.service.UserArticleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserArticleServiceImpl implements UserArticleService {
    private final UserArticleMapper userArticleMapper;
    private final IdentityService identityService;
    @Autowired
    public UserArticleServiceImpl(UserArticleMapper userArticleMapper, IdentityService identityService) {
        this.userArticleMapper = userArticleMapper;
        this.identityService = identityService;
    }

    // 获取最新第skip篇后的num篇文章
    @Override
    public List<UserArticle> getArticles(Integer skip, Integer num) {
        return userArticleMapper.getArticles(skip, num);
    }

    @Override
    public List<UserArticle> getArticlesByUserId(String userId, Integer skip, Integer num) {
        return userArticleMapper.getArticlesByUserId(userId, skip, num);
    }

    @Override
    public String setArticle(HttpServletRequest request, String userId, String title, String content) throws Exception {
        if (identityService.check(request, userId, IdentityService.ById)) {
            String id = String.valueOf(UUID.randomUUID());
            userArticleMapper.setArticle(id, userId, title, content);
            return id;
        } else {
            return null;
        }
    }

    @Override
    public UserArticle getArticle(String id) {
        return userArticleMapper.getArticle(id);
    }

    @Override
    public UserArticle updateArticle(HttpServletRequest request, String id, String title, String content, Integer dislike) throws Exception {
        String userId = getArticle(id).getAuthor();
        if (identityService.check(request, userId, IdentityService.ById)) {
            userArticleMapper.updateUserArticle(id, title, content, dislike);
            return userArticleMapper.getArticle(id);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteArticle(HttpServletRequest request, String id) throws Exception {
        String userId = getArticle(id).getAuthor();
        if (identityService.check(request, userId, IdentityService.ById)) {
            userArticleMapper.deleteArticle(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void CancelledArticle(String articleId) {
        userArticleMapper.cancelledArticle(articleId);
    }
}
