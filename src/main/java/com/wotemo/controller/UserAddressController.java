package com.wotemo.controller;

import com.wotemo.pojo.Result;
import com.wotemo.pojo.UserAddress;
import com.wotemo.service.UserAddressService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserAddressController {
    private final UserAddressService userAddressService;
    @Autowired
    public UserAddressController(UserAddressService userAddressService){
        this.userAddressService = userAddressService;
    }

    @GetMapping("/get/address")
    public Result getAddress(@RequestParam String id) throws Exception {
        log.info("获取用户地址");
        return Result.success(userAddressService.getAddress(id));
    }

    @PostMapping("/update/address")
    public Result updateAddress(HttpServletRequest request,
                                @RequestParam String id,
                                @RequestParam String province,
                                @RequestParam String city,
                                @RequestParam(required = false) String fullAddress) throws Exception {
        log.info("更新用户地址");
        UserAddress userAddress = userAddressService.updateAddress(request, id, province, city, fullAddress);
        if (userAddress != null) {
            return Result.success(userAddress);
        } else {
            return Result.error("身份验证失败");
        }
    }
}
