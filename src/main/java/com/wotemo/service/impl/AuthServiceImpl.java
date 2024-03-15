package com.wotemo.service.impl;

import com.wotemo.pojo.*;
import com.wotemo.service.*;
import com.wotemo.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final RedisTemplate<String, String> redisTemplate;
    private final CheckCodeService checkCodeService;
    private final UserAddressService userAddressService;
    private final UserInfoService userInfoService;
    private final UserService userService;
    private final UserPasswordService userPasswordService;
    private final IdentityService identityService;
    private final UserLikeService userLikeService;
    private final UserArticleService userArticleService;
    private final UserCommentService userCommentService;
    @Autowired
    public AuthServiceImpl(RedisTemplate<String, String> redisTemplate, CheckCodeService checkCodeService, UserAddressService userAddressService, UserInfoService userInfoService, UserService userService, UserPasswordService userPasswordService, IdentityService identityService, UserLikeService userLikeService, UserArticleService userArticleService, UserCommentService userCommentService){
        this.redisTemplate = redisTemplate;
        this.checkCodeService = checkCodeService;
        this.userAddressService = userAddressService;
        this.userInfoService = userInfoService;
        this.userService = userService;
        this.userPasswordService = userPasswordService;
        this.identityService = identityService;
        this.userLikeService = userLikeService;
        this.userArticleService = userArticleService;
        this.userCommentService = userCommentService;
    }

    @Value("${jwt.secret-key}")
    String SECRET_KEY; // JWT密钥
    @Value("${jwt.ttl}")  // JWT过期时间
    long exp;

    @Override
    public String loginByPassword(String username, String email, String password) throws Exception {
        log.info("用户名为: {}, 邮箱为: {}, 尝试使用密码: {} 登录", username, email, password);
        if(username == null && email == null || username !=null && email != null){
            throw new Exception("没有或有且超过一个数据被请求,请仅传入一个值");
        }
        UserPassword userPassword = userPasswordService.getPassword(username, email);
        if(userPassword.getPassword().equals(password)){
            JwtUtils jwtUtils = new JwtUtils();
            jwtUtils.setSECRET_KEY(SECRET_KEY);
            System.out.println(jwtUtils.getSECRET_KEY());
            String jwt = jwtUtils.generateJWT(userPassword.getUserId(), username, exp);
            return jwt;
        } else {
            return null;
        }
    }

    @Override
    public String loginByCheckCode(String email, String codeId, String code) throws Exception {
        log.info("邮箱为: {}, 尝试使用验证码: {} 登录", email, code);
        if (email == null) {
            throw new Exception("缺失邮箱");
        } else if (codeId == null) {
            throw new Exception("缺失验证码ID");
        } else if (code == null) {
            throw new Exception("缺失验证码");
        }
        UserPassword userPassword = userPasswordService.getPassword(null, email);
        if(checkCodeService.checkCode(codeId, code, email)){
            JwtUtils jwtUtils = new JwtUtils();
            jwtUtils.setSECRET_KEY(SECRET_KEY);
            System.out.println(jwtUtils.getSECRET_KEY());
            String jwt = jwtUtils.generateJWT(userPassword.getUserId(), userPassword.getUsername(), exp);
            return jwt;
        } else {
            return null;
        }
    }

    @Value("${jwt.ttl}")
    long ttl; // 过期时间

    @Override
    public void logout(String jwt) {
        redisTemplate.opsForValue().set(jwt, "", ttl, TimeUnit.SECONDS);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String register(String username, String email, String password,
                           String nickname, String avatar, LocalDate birthday, String profile,
                           String codeId, String code,
                           String province, String city, String fullAddress) throws Exception {
        log.info("用户名为: {}, 尝试注册账号", username);
        if(checkCodeService.checkCode(codeId, code, email)){
            UserAddress userAddress = userAddressService.setAddress(province, city, fullAddress);
            User user = userService.setUser(username, email);
            userInfoService.setUserInfo(user.getId(), nickname, avatar, birthday, userAddress.getId(), profile);
            userPasswordService.setPassword(user.getId(), user.getUsername(), user.getEmail(), password);

            JwtUtils jwtUtils = new JwtUtils();
            jwtUtils.setSECRET_KEY(SECRET_KEY);
            System.out.println(jwtUtils.getSECRET_KEY());
            String jwt = jwtUtils.generateJWT(user.getId(), username, exp);

            return jwt;
        } else {
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeAccount(HttpServletRequest request, String username, String password, String email, String codeId, String code) throws Exception {
        if (!checkCodeService.checkCode(codeId, code, email)) {
            throw new Exception("验证码错误");
        }

        User user = userService.getUser(null, username, null);
        String userId = user.getId();
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        List<UserArticle> articles = userArticleService.getArticlesByUserId(userId, null, null);
        if (identityService.check(request, userId, IdentityService.ById)) {
            userAddressService.deleteAddress(userInfo.getAddress());
            userInfoService.deleteUserInfo(userId);
            userPasswordService.deletePassword(userId);
            userLikeService.cancelledLike(userId);
            userCommentService.cancelledComment(userId);
            for (UserArticle article : articles) {
                String articleId = article.getId();
                userLikeService.deleteArticleLikes(articleId);
                userCommentService.deleteArticleComments(articleId);
                userArticleService.CancelledArticle(articleId);
            }
            userService.deleteUser(userId);
            return true;
        } else {
            return false;
        }
    }

}
