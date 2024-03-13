package com.wotemo.utils;

import cn.leancloud.LCFile;
import cn.leancloud.LCLogger;
import cn.leancloud.core.LeanCloud;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@Data
//@ConfigurationProperties(prefix = "leancloud.initialize") 新建LeanCloudProperties类 依赖注入
public class LeanCloudUtils {
    private final String errorMessage = "文件上传失败，可能是文件无法被读取，或者上传过程中出现问题";
//    @Value("${leancloud.initialization.app-id}")
//    private String appId;
//    @Value("${leancloud.initialization.app-key}")
//    private String appKey;
//    @Value("${leancloud.initialization.server-url}")
//    private String serverUrl;

    public void Init(String appId, String appKey, String serverUrl){
//        LeanCloud.initialize(appId, appKey, serverUrl);
        LeanCloud.initialize(appId, appKey, serverUrl);
        LeanCloud.setLogLevel(LCLogger.Level.DEBUG);
    }
    @Async
    public CompletableFuture<String> upload(MultipartFile file) throws Exception {
        CompletableFuture<String> stringCompletableFuture = new CompletableFuture<>();
        // 获取文件字节码
        byte[] fileBytes = file.getBytes();
        // 生成随机文件名
        String OriginalFilename = file.getOriginalFilename();
        int extIndex;
        if (OriginalFilename != null) {
            extIndex = OriginalFilename.lastIndexOf(".");
        } else {
            throw new Exception(errorMessage);
        }
        UUID uuid = UUID.randomUUID();
        String filename;
        filename = uuid + OriginalFilename.substring(extIndex);
        // 创建LeanCloud文件
        LCFile LCfile = new LCFile(filename, fileBytes);
        LCfile.saveInBackground().subscribe(new Observer<LCFile>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(LCFile file) {
                System.out.println("文件保存完成 URL: " + file.getUrl());
                stringCompletableFuture.complete(file.getUrl());
            }
            public void onError(Throwable throwable) {
                // 保存失败，可能是文件无法被读取，或者上传过程中出现问题
                try {
                    throw new Exception(errorMessage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            public void onComplete() {}
        });
        return stringCompletableFuture;
    }
}
