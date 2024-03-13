package com.wotemo.exception;

import com.wotemo.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result ex(Exception e){
        log.info("出现异常: {}",e.getMessage());
        return Result.error("出现异常, 请联系管理员");
    }
}
