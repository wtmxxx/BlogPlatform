# 一款完备的SpringBoot博客平台

### 一. 功能

1.  用户注册与登录（JWT令牌单点登录）、注销
2.  修改用户信息（管理员有更多权限）
3.  发表文章（有点踩）
4.  发表评论（可回复）
5.  上传文件

### 二. 使用工具

1.  Springboot
2.  Mybatis
3.  Redis
4.  Mysql
5.  Leancloud
6.  Postman（其他亦可）

### 三. Mysql数据库

表结构如下：

```mysql


create table user
(
    id                varchar(255)                         comment '用户的唯一标识ID'
        primary key,
    username          varchar(20) unique                   not null comment '唯一用户名',
    email             varchar(255)                         not null comment '用户邮箱',
    is_administrator  tinyint(1) default 0                 not null comment '用户是否为管理员',
    registration_time datetime   default CURRENT_TIMESTAMP not null comment '用户注册时间',
    update_time       datetime   default CURRENT_TIMESTAMP not null comment '用户修改数据时间'
)
    comment '用户信息';

create table user_info
(
    user_id          varchar(255)                                                                   comment '用户个人信息唯一标识ID'
        primary key,
    nickname    varchar(20)                                                                    null comment '用户昵称',
    avatar      varchar(510) default 'https://img.wotemo.com/blog-platform/default_avatar.png' null comment '用户头像',
    birthday    date                                                                           not null comment '用户生日',
    address     varchar(255)                                                                   null comment '用户居住地(外键)',
    profile     varchar(200) default '该用户没有填写个人简介'                                       null comment '用户个人简介',
    create_time datetime     default CURRENT_TIMESTAMP                                         not null comment '用户个人信息创建时间',
    update_time datetime     default CURRENT_TIMESTAMP                                         not null comment '用户个人信息更新时间'
)
    comment '用户个人信息';

create table user_address
(
    id           varchar(255) comment '用户地址唯一标识'
        primary key,
    province     varchar(20)  not null comment '用户地址-省份',
    city         varchar(20)  not null comment '用户地址-城市',
    full_address varchar(255) null comment '用户详细地址'
)
    comment '用户地址';

create table user_article
(
    id          varchar(255)                       comment '文章唯一标识ID'
        primary key,
    author      varchar(255)                       not null comment '发表文章的用户ID',
    title       text                               not null comment '文章标题',
    content     mediumtext                         null comment '文章内容',
    dislike     int      default 0                 null comment '文章点踩(仅表示数量)',
    create_time datetime default CURRENT_TIMESTAMP not null comment '文章创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null comment '文章更新时间'
)
    comment '用户发表的文章';

create table user_comment
(
    id             varchar(255)  comment '评论唯一标识ID'
        primary key,
    article_id     varchar(255)  not null comment '评论所属文章ID(外键)',
    user_id        varchar(255)  not null comment '发表用户ID(外键)',
    content        text          not null comment '评论内容',
    parent_id      varchar(255)  null comment '回复评论ID(外键)',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '评论发表时间'
)
    comment '用户发表的评论';

create table user_like
(
    user_id     varchar(255)                       not null comment '点赞用户ID(外键)',
    article_id  varchar(255)                       not null comment '点赞文章ID(外键)',
    create_time datetime default CURRENT_TIMESTAMP not null comment '点赞时间',
    primary key (user_id, article_id)
)
    comment '用户文章点赞';


create table user_password
(
    user_id     varchar(255)                       comment '用户唯一ID'
        primary key,
    username    varchar(20)                        not null comment '用户名',
    email       varchar(255)                       not null comment '用户邮箱',
    password    varchar(16)                        not null comment '用户密码',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '用户密码';
```

### 四. 修改必要数据

**application.yaml**

- 修改spring.datasource下的username和password等为你自己的mysql数据库账号密码等
- 修改spring.data.redis下的port和password等为你自己的redis端口密码等
- 修改spring.mail下的username、password、from等信息以发送邮箱
- 修改leancloud.initialization下的app-id、app-key、server-url为你自己的（Leancloud仅用于文件存储）
- （可选）jwt下的secret-key（密钥，用于生成jwt）和ttl（失效时间）

### 五. Postman API

项目的全部API在根目录下的postman文件夹中，可以在postman中导入

### 六. 代码获取

- [Github](https://github.com/wtmxxx/BlogPlatform)：https://github.com/wtmxxx/BlogPlatform
