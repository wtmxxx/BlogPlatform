package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.service.UserService;
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
    public Result updateUser(@RequestParam String username, @RequestParam String email) throws Exception {
        log.info("更新用户");
        return Result.success(userService.updateUser(username, email));
    }
}
