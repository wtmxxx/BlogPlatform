package com.wotemo.mapper;

import com.wotemo.pojo.UserArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserArticleMapper {
    // 获取最新第skip篇后的num篇文章
    @Select("select * from user_article order by create_time desc limit #{skip}, #{num}")
    public List<UserArticle> getArticles(Integer skip, Integer num);

    void setArticle(String id, String userId, String title, String content);

    UserArticle getArticle(String id);

    void updateUserArticle(String id, String title, String content, Integer dislike);

    void deleteArticle(String id);

    List<UserArticle> getArticlesByUserId(String userId, Integer skip, Integer num);

    void cancelledArticle(String articleId);
}
