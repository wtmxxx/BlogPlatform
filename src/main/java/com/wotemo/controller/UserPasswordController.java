package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.service.UserPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserPasswordController {
    private final UserPasswordService userPasswordService;
    @Autowired
    public UserPasswordController(UserPasswordService userPasswordService){
        this.userPasswordService = userPasswordService;
    }
//    @PostMapping("/set/password")
//    public Result setPassword(String username, String email){
//        userPasswordService.setPassword(username, email);
//        return Result.success(null, "密码设置成功");
//    }

    @PostMapping("/update/password/username")
    public Result updatePasswordByUsername(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword){
        log.info("用户: {}, 尝试修改密码", username);
        if (userPasswordService.updatePasswordByUsername(username, oldPassword, newPassword)) {
            return Result.success(null, "密码修改成功");
        } else {
            return Result.error( "密码修改失败");
        }

    }

    @PostMapping("/update/password/email")
    public Result updatePasswordByEmail(@RequestParam String email, @RequestParam String password, @RequestParam String codeId, @RequestParam String code){
        log.info("邮箱: {}, 尝试修改密码", email);
        if (userPasswordService.updatePasswordByEmail(email, password, codeId, code)) {
            return Result.success(null, "密码修改成功");
        } else {
            return Result.error( "密码修改失败");
        }
    }
}
