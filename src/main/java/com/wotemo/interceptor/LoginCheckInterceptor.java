package com.wotemo.interceptor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wotemo.pojo.Result;
import com.wotemo.utils.JwtUtils;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret-key}")
    String SECRET_KEY;
    JwtUtils jwtUtils = new JwtUtils();

    RedisTemplate<String, String> redisTemplate;
    @Autowired
    public LoginCheckInterceptor(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    @Override // 前 true: 放行  false: 不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截到请求...");

        String jwt = request.getHeader("token");

        // 判断JWT是否为空，或者是否在小黑屋（注销）
        if (jwt == null || Boolean.TRUE.equals(redisTemplate.hasKey(jwt))) {
            if(jwt != null) {
                log.info("此JWT已被手动注销, 在Redis中被查询到");
            }
            Gson gson = new GsonBuilder().serializeNulls().create();
            String NOT_LOGIN = gson.toJson(Result.error("NOT_LOGIN"));
            response.setHeader("Content-Type", "application/json");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(NOT_LOGIN);
            return false;
        }
        jwtUtils.setSECRET_KEY(SECRET_KEY);
        try {
            Jwt<Header, Map<String, Object>> parsedJwt = jwtUtils.parseJWT(jwt);
            Header header = parsedJwt.getHeader();
            Map<String, Object> payload = parsedJwt.getPayload();
            // 判断Payload是否合法
            if (
                    !header.getAlgorithm().equals("HS256")
                            || Integer.parseInt(payload.get("exp").toString()) > System.currentTimeMillis()
                            || payload.get("sub") == null
                            || payload.get("username") == null
                            || !payload.get("iss").equals("BlogPlatform")
                            || payload.get("iat") == null
            ) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                String NOT_LOGIN = gson.toJson(Result.error("NOT_LOGIN"));
                response.setHeader("Content-Type", "application/json");
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(NOT_LOGIN);
                return false;
            }
        } catch (Exception e) {
            log.info("JWT数据或被修改: {}", e.getMessage());
            Gson gson = new GsonBuilder().serializeNulls().create();
            String NOT_LOGIN = gson.toJson(Result.error("NOT_LOGIN"));
            response.setHeader("Content-Type", "application/json");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(NOT_LOGIN);
            return false;
        }
        log.info("JWT合法, 放行");
        return true;
    }

    @Override // 后
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle...");
    }

    @Override // 最后
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion...");
    }
}
