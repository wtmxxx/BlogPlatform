package com.wotemo.mapper;

import com.wotemo.pojo.UserComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserCommentMapper {
    // 获取指定用户的评论(第skip条后的num条) ID
    @Select("select * from user_comment where user_id=#{id} order by create_time desc limit #{skip}, #{num}")
    public List<UserComment> getUserComments(String id, Integer skip, Integer num);

    // 获取指定文章的评论(第skip条后的num条) ID
    // @Select UserMapper.xml
    public List<UserComment> getArticleComments(String id, Integer skip, Integer num);

    // 获取指定评论的评论(即回复，第skip条后的num条) ID
    // @Select UserMapper.xml
    public List<UserComment> getCommentComments(String id, Integer skip, Integer num);
}
