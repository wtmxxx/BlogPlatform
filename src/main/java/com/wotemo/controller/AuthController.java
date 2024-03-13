package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.service.AuthService;
import com.wotemo.service.CheckCodeService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Slf4j
@RestController
public class AuthController {
    private final AuthService authService;
    private final CheckCodeService checkCodeService;
    @Autowired
    public AuthController(AuthService authService, CheckCodeService checkCodeService){
        this.authService = authService;
        this.checkCodeService = checkCodeService;
    }

//    @Value("${jwt.secret-key}")
//    String SECRET_KEY; // JWT密钥
//    @Value("${jwt.ttl}")  // JWT过期时间
//    long exp;

    @RequestMapping("/verify-code")
    public Result generateCode(String email) throws MessagingException {
        String id =  checkCodeService.addRandomCode(email, 1800);
        return Result.success(id,"验证码获取成功");
    }

    @PostMapping("/login/password")
    public Result loginByPassword(String username, String email, String password) throws Exception {
        log.info("用户: {}, 尝试登录", username);

        String jwt = authService.loginByPassword(username, email, password);
        if(jwt != null){
            return Result.success(jwt);
        } else {
            return Result.error("用户名(邮箱)或密码错误");
        }
    }

    @PostMapping("/login/CheckCode")
    public Result loginByCheckCode(String email, String codeId, String code) throws Exception {
        log.info("邮箱: {}, 尝试登录", email);

        String jwt = authService.loginByCheckCode(email, codeId, code);
        if(jwt != null){
            return Result.success(jwt);
        } else {
            return Result.error("用户名(邮箱)或密码错误");
        }
    }

    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request){
        log.info("用户登出");
        String jwt = request.getHeader("token");
        authService.logout(jwt);
        return Result.success();
    }

    @PostMapping("/register")
    public Result register(@RequestParam String username, @RequestParam String email, @RequestParam String password,
                           @RequestParam String nickname, @RequestParam String avatar, @RequestParam LocalDate birthday, @RequestParam(required = false) String profile,
                           @RequestParam String codeId, @RequestParam String code,
                           @RequestParam String province, @RequestParam String city, @RequestParam(required = false) String full_address) throws Exception {

        String jwt = authService.register(username, email, password,
                             nickname, avatar, birthday, profile,
                             codeId, code,
                             province, city, full_address);

        if(jwt != null){
            return Result.success(jwt);
        } else {
            return Result.error("验证码错误");
        }
    }
}
