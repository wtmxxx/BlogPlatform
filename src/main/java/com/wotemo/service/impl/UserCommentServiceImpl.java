package com.wotemo.service.impl;

import com.wotemo.mapper.UserCommentMapper;
import com.wotemo.pojo.Result;
import com.wotemo.pojo.UserComment;
import com.wotemo.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCommentServiceImpl implements UserCommentService {
    private final UserCommentMapper userCommentMapper;
    @Autowired
    public UserCommentServiceImpl(UserCommentMapper userCommentMapper) {
        this.userCommentMapper = userCommentMapper;
    }

    // 获取指定用户或文章或评论的评论(第skip条后的num条) ID
    @Override
    public List<UserComment> getComments(String obj, String id, Integer skip, Integer num) throws Exception {
        if(obj.equals("user")){
            return userCommentMapper.getUserComments(id, skip, num);
        } else if (obj.equals("article")) {
            return userCommentMapper.getArticleComments(id, skip, num);
        } else if (obj.equals("comment")) {
            return userCommentMapper.getCommentComments(id, skip, num);
        } else {
            throw new Exception("对象选取错误");
        }
    }

}
