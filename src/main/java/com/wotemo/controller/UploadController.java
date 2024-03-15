package com.wotemo.controller;


import com.wotemo.pojo.Result;
import com.wotemo.utils.LeanCloudUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;


@Slf4j
@RestController
public class UploadController {
    private final LeanCloudUtils leanCloudUtils = new LeanCloudUtils();
    @Value("${leancloud.initialization.app-id}")
    private String appId;
    @Value("${leancloud.initialization.app-key}")
    private String appKey;
    @Value("${leancloud.initialization.server-url}")
    private String serverUrl;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        leanCloudUtils.Init(appId, appKey, serverUrl);

        log.info("文件上传");
        CompletableFuture<String> stringFuture = leanCloudUtils.upload(file);
        String url = stringFuture.join();
        return Result.success(url);
    }

}
