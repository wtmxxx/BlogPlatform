package com.wotemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@ServletComponentScan
@EnableAsync
public class BlogPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogPlatformApplication.class, args);
    }

}
