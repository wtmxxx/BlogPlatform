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

}
