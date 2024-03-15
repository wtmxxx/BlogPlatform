package com.wotemo.service;

import com.wotemo.pojo.UserComment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserCommentService {
    List<UserComment> getComments(String obj, String id, Integer skip, Integer num) throws Exception;

    UserComment getComment(String id);

    String setComment(HttpServletRequest request, String articleId, String userId, String content, String parentId) throws Exception;

    boolean deleteComment(HttpServletRequest request, String id) throws Exception;

    void deleteArticleComments(String articleId);

    void cancelledComment(String userId);
}
