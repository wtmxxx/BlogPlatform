package com.wotemo.service;

import com.wotemo.pojo.UserLike;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserLikeService {
    List<UserLike> getLikes(String obj, String id, Integer skip, Integer num) throws Exception;

    Integer getLikesCount(String obj, String id) throws Exception;

    boolean setUserLike(HttpServletRequest request, String userId, String articleId) throws Exception;

    boolean getLike(String userId, String articleId);
}
