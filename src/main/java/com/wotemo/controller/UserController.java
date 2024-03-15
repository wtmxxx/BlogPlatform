package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.pojo.User;
import com.wotemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/get/user")
    public Result getUser(String id, String username, String email) throws Exception {
        log.info("获取用户");
        return Result.success(userService.getUser(id, username, email));
    }

    @PostMapping("/update/user")
    public Result updateUser(HttpServletRequest request,
                             @RequestParam String id,
                             @RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String codeId,
                             @RequestParam String code) throws Exception {
        log.info("更新用户");
        User user = userService.updateUser(request, id, username, email, codeId, code);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("身份验证失败");
        }
    }

    @PostMapping("/set/administrator")
    public Result setAdministrator(HttpServletRequest request, @RequestParam String id) throws Exception {
        log.info("设置管理员");
        if (userService.setAdministrator(request, id)) {
            return Result.success(null, "管理员设置成功！");
        } else {
            return Result.error("管理员设置失败！");
        }
    }

}
