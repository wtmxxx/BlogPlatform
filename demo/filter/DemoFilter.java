package com.wotemo.filter;

import cn.leancloud.json.JSON;
import com.wotemo.pojo.Result;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;


@Slf4j
@WebFilter(urlPatterns = "/*")
public class DemoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取请求的URL
        String url = request.getRequestURL().toString();
        log.info("请求的url：{}",url);

        // 判断是否为登录操作
        if(url.contains("/login")){
            log.info("登录操作，放行...");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //获取请求头中的令牌
        String jwt = request.getHeader("token");

        if(!StringUtils.hasLength(jwt)){
            log.info("请求头为空，返回未登录的信息");
            String NOT_LOGIN = JSON.toJSONString(Result.error("NOT_LOGIN"));
            response.setHeader("Content-Type","Application/JSON");
            response.getWriter().write(NOT_LOGIN);
            return;
        }

        // 解析JWT令牌
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
