package com.wotemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code; // 成功：1，失败：0
    private String msg; // 提示信息
    private Object data; // 返回数据

    public static Result success(){ // 增删改 成功响应
        return new Result(1,"Success!",null);
    }

    public static Result success(Object data){ // 查询 成功响应
        return new Result(1,"Success!",data);
    }

    public static Result success(Object data,String msg){ // 查询 成功响应 自定义返回信息
        return new Result(1,msg,data);
    }

    public static Result error(String msg){ // 失败响应
        return new Result(0,msg,null);
    }
}
