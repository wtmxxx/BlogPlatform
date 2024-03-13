package com.wotemo.service;

import com.wotemo.pojo.UserComment;

import java.util.List;

public interface UserCommentService {
    List<UserComment> getComments(String obj, String id, Integer skip, Integer num) throws Exception;
}
