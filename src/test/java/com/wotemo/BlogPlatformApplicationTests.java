package com.wotemo;

import com.wotemo.pojo.UserAddress;
import com.wotemo.service.AuthService;
import com.wotemo.service.CheckCodeService;
import com.wotemo.service.UserAddressService;
import com.wotemo.service.UserLikeService;
import com.wotemo.utils.JwtUtils;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class BlogPlatformApplicationTests {

    @Test
    void contextLoads() {
    }

    @Value("${jwt.secret-key}")
    String SECRET_KEY;
    @Value("${jwt.ttl}")
    long exp;

    @Test
    void testJwtUtils(){
        JwtUtils jwtUtils = new JwtUtils();
        jwtUtils.setSECRET_KEY(SECRET_KEY);
        System.out.println(jwtUtils.getSECRET_KEY());
        String jwt = jwtUtils.generateJWT("1","SHR", exp);
        System.out.println(jwt);
        Jwt<Header, Map<String, Object>> parsed_jwt = jwtUtils.parseJWT(jwt);
        Header header = parsed_jwt.getHeader();
        System.out.println(header);
        Map<String, Object> payload = parsed_jwt.getPayload();
        System.out.println(payload.get("iss"));
        System.out.println(payload);
    }


    @Autowired
    CheckCodeService checkCodeService;

    @Test
    void testCheckCodeServiceImpl(){
//        checkCodeService.addCode(String.valueOf(4),"11488",1800);
//        System.out.println(checkCodeService.checkCode("7c19a422-04ea-47ec-ae36-5ffa7763249d","066294", "bestman@88.com"));
        checkCodeService.deleteCode("7c19a422-04ea-47ec-ae36-5ffa7763249d");
    }

    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Test
    void testBlackRoom(){
        redisTemplate.opsForValue().set("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlcm5hbWUiOiJTSFIiLCJleHAiOjE3MTAwMTk0MDUsImlhdCI6MTcwOTk3NjIwNSwiaXNzIjoiQmxvZ1BsYXRmb3JtIn0.ibM1vI9uNBbYNuflA9NV63fccUm4_o-uPNE61hhh-H8", "", 12 * 3600 * 1000, TimeUnit.SECONDS);
    }

    @Autowired
    UserAddressService userAddressService;

    @Test
    void testUserAddressServiceImpl() throws Exception {
        UserAddress userAddress = userAddressService.setAddress("湖北省", "襄阳市", "湖北省襄阳市襄州区");
        System.out.println(userAddress);
    }

    @Autowired
    UserLikeService userLikeService;

    @Test
    void testGetUserLike(){
        userLikeService.getLike("1", "1");
    }


}
