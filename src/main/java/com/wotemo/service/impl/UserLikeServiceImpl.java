package com.wotemo.service.impl;

import com.wotemo.mapper.UserLikeMapper;
import com.wotemo.pojo.UserLike;
import com.wotemo.service.IdentityService;
import com.wotemo.service.UserLikeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLikeServiceImpl implements UserLikeService {
    private final UserLikeMapper userLikeMapper;
    private final IdentityService identityService;
    @Autowired
    public UserLikeServiceImpl(UserLikeMapper userLikeMapper, IdentityService identityService) {
        this.userLikeMapper = userLikeMapper;
        this.identityService = identityService;
    }

    // 获取指定用户或文章或评论的评论(第skip条后的num条) ID
    @Override
    public List<UserLike> getLikes(String obj, String id, Integer skip, Integer num) throws Exception {
        if(obj.equals("user")){
            return userLikeMapper.getUserLikes(id, skip, num);
        } else if (obj.equals("article")) {
            return userLikeMapper.getArticleLikes(id, skip, num);
        } else {
            throw new Exception("对象选取错误");
        }
    }

    @Override
    public boolean getLike(String userId, String articleId) {
        return userLikeMapper.getLike(userId, articleId) != null;
    }

    @Override
    public void cancelledLike(String userId) {
        userLikeMapper.cancelledLike(userId);
    }

    @Override
    public void deleteArticleLikes(String articleId) {
        userLikeMapper.deleteArticleLikes(articleId);
    }


    @Override
    public Integer getLikesCount(String obj, String id) throws Exception {
        if(obj.equals("user")){
            return userLikeMapper.getUserLikesCount(id);
        } else if (obj.equals("article")) {
            return userLikeMapper.getArticleLikesCount(id);
        } else {
            throw new Exception("对象选取错误");
        }
    }

    @Override
    public boolean setUserLike(HttpServletRequest request, String userId, String articleId) throws Exception {
        if (identityService.check(request, userId, IdentityService.ById)) {
            if (userLikeMapper.getLike(userId, articleId) == null){
                userLikeMapper.setUserLike(userId, articleId);
            } else {
                userLikeMapper.deleteLike(userId, articleId);
            }
            return true;
        } else {
            return false;
        }
    }


}
