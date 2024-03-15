package com.wotemo.mapper;

import com.wotemo.pojo.UserLike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserLikeMapper {
    // 获取用户的点赞
    public List<UserLike> getUserLikes(String id, Integer skip, Integer num);
    // 获取文章的点赞
    public List<UserLike> getArticleLikes(String id, Integer skip, Integer num);
    // 获取点赞
    public UserLike getLike(String userId, String articleId);
    // 获取用户的点赞数量
    public Integer getUserLikesCount(String id);
    // 获取文章的点赞数量
    public Integer getArticleLikesCount(String id);

    void setUserLike(String userId, String articleId);

    void deleteLike(String userId, String articleId);

    void cancelledLike(String userId);

    void deleteArticleLikes(String articleId);
}
