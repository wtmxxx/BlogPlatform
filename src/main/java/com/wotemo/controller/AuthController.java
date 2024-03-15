package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.service.AuthService;
import com.wotemo.service.CheckCodeService;
import com.wotemo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Slf4j
@RestController
public class AuthController {
    private final AuthService authService;
    private final CheckCodeService checkCodeService;

    @Autowired
    public AuthController(AuthService authService, CheckCodeService checkCodeService, UserService userService) {
        this.authService = authService;
        this.checkCodeService = checkCodeService;
    }

//    @Value("${jwt.secret-key}")
//    String SECRET_KEY; // JWT密钥
//    @Value("${jwt.ttl}")  // JWT过期时间
//    long exp;

    @RequestMapping("/verify-code")
    public Result generateCode(String email) throws MessagingException {
        String id = checkCodeService.addRandomCode(email, 1800);
        return Result.success(id, "验证码获取成功");
    }

//    @RequestMapping("/remove/verify-code")
//    public Result removeCode(@RequestParam String username) throws Exception {
//        String email = userService.getUser(null, username, null).getEmail();
//        String id = checkCodeService.addRandomCode(email, 1800);
//        return Result.success(id, "验证码获取成功");
//    }

    @PostMapping("/login/password")
    public Result loginByPassword(String username, String email, String password) throws Exception {
        log.info("用户: {}, 尝试登录", username);

        String jwt = authService.loginByPassword(username, email, password);
        if (jwt != null) {
            return Result.success(jwt);
        } else {
            return Result.error("用户名(邮箱)或密码错误");
        }
    }

    @PostMapping("/login/checkCode")
    public Result loginByCheckCode(String email, String codeId, String code) throws Exception {
        log.info("邮箱: {}, 尝试登录", email);

        String jwt = authService.loginByCheckCode(email, codeId, code);
        if (jwt != null) {
            return Result.success(jwt);
        } else {
            return Result.error("用户名(邮箱)或密码错误");
        }
    }

    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request) {
        log.info("用户登出");
        String jwt = request.getHeader("token");
        authService.logout(jwt);
        return Result.success();
    }

    @PostMapping("/register")
    public Result register(@RequestParam String username, @RequestParam String email, @RequestParam String password,
                           @RequestParam String nickname, @RequestParam String avatar,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                           @RequestParam(required = false) String profile,
                           @RequestParam String codeId, @RequestParam String code,
                           @RequestParam String province, @RequestParam String city, @RequestParam(required = false) String fullAddress) throws Exception {

        String jwt = authService.register(username, email, password,
                nickname, avatar, birthday, profile,
                codeId, code,
                province, city, fullAddress);

        if (jwt != null) {
            return Result.success(jwt);
        } else {
            return Result.error("验证码错误");
        }
    }

    @DeleteMapping("/remove/account")
    public Result deleteAccount(HttpServletRequest request,
                                @RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String codeId,
                                @RequestParam String email,
                                @RequestParam String code) throws Exception {
        log.info("彻底删除账户");
        if (authService.removeAccount(request, username, password, email, codeId, code)) {
            return Result.success(null, "账户删除成功！");
        } else {
            return Result.error("账户删除失败");
        }
    }
}
