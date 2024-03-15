# 博客平台API接口文档1.0

UserController：
负责处理与用户相关的请求，如用户注册、登录、信息修改等。
调用UserService处理业务逻辑。

UserInfoController：
处理用户个人信息相关的请求，如查看个人信息、修改昵称、上传头像等。
调用UserInfoService处理业务逻辑。

UserAddressController：
处理用户地址相关的请求，如添加新地址、编辑地址信息等。
调用UserAddressService处理业务逻辑。

UserArticleController：
处理用户文章相关的请求，如发表文章、编辑文章、删除文章等。
调用UserArticleService处理业务逻辑。

UserCommentController：
处理用户评论相关的请求，如发表评论、回复评论、删除评论等。
调用UserCommentService处理业务逻辑。

UserLikeController：
处理用户点赞相关的请求，如点赞文章、取消点赞等。
调用UserLikeService处理业务逻辑。