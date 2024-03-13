package com.wotemo.controller;

import com.wotemo.pojo.Result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class SessionController {
    @GetMapping("/setCookie")
    public Result setCookie(HttpServletResponse response){
        response.addCookie(new Cookie("username", "root"));
        return Result.success();
    }

    @GetMapping("/getCookie")
    public Result getCookie(HttpServletRequest request){
        List<Cookie> cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("username")).toList();
        System.out.println(cookie.getFirst().getName() + " " + cookie.getFirst().getValue());
        return Result.success(cookie.getFirst());
    }

    @GetMapping("/setSession")
    public Result setSession(HttpSession session){
        log.info("HttpSession-setSession: {}",session.hashCode());
        session.setAttribute("username","root");
        return Result.success();
    }

    @GetMapping("/getSession")
    public Result getSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        log.info("HttpSession-getSession: {}",session.hashCode());
        Object loginUser = session.getAttribute("username");
        log.info("loginUser: {}",loginUser);
        return Result.success(loginUser);
    }
}
