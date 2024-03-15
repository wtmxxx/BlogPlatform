package com.wotemo.service.impl;

import com.wotemo.mapper.UserCommentMapper;
import com.wotemo.pojo.Result;
import com.wotemo.pojo.UserComment;
import com.wotemo.service.IdentityService;
import com.wotemo.service.UserCommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserCommentServiceImpl implements UserCommentService {
    private final UserCommentMapper userCommentMapper;
    private final IdentityService identityService;
    @Autowired
    public UserCommentServiceImpl(UserCommentMapper userCommentMapper, IdentityService identityService) {
        this.userCommentMapper = userCommentMapper;
        this.identityService = identityService;
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

    @Override
    public UserComment getComment(String id) {
        return userCommentMapper.getComment(id);
    }

    @Override
    public String setComment(HttpServletRequest request, String articleId, String userId, String content, String parentId) throws Exception {
        if (identityService.check(request, userId, IdentityService.ById)) {
            String id = String.valueOf(UUID.randomUUID());
            if (parentId.isEmpty()) { parentId = null; }
            userCommentMapper.setComment(id, articleId, userId, content, parentId);
            return id;
        } else {
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteComment(HttpServletRequest request, String id) throws Exception {
        String userId = getComment(id).getUserId();
        if (identityService.check(request, userId, IdentityService.ById)) {
            userCommentMapper.deleteCommentByParentId(id);
            userCommentMapper.deleteComment(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteArticleComments(String articleId) {
        userCommentMapper.deleteArticleComments(articleId);
    }

    @Override
    public void cancelledComment(String userId) {
        userCommentMapper.cancelledUserComment(userId);
        userCommentMapper.cancelledParentUserComment(userId);
    }

}
