package com.wotemo.controller;

import com.wotemo.pojo.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserTest {
    @DeleteMapping("/user/{ids}")
    public Result deleteUsers(@PathVariable List<Integer> ids){
        return Result.success(ids);
    }
}
