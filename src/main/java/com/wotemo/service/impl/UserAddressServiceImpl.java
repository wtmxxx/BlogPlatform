package com.wotemo.service.impl;


import com.wotemo.mapper.UserAddressMapper;
import com.wotemo.pojo.UserAddress;
import com.wotemo.service.IdentityService;
import com.wotemo.service.UserAddressService;
import com.wotemo.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressMapper userAddressMapper;
    private final IdentityService identityService;
    @Autowired
    public UserAddressServiceImpl(UserAddressMapper userAddressMapper, IdentityService identityService, UserInfoService userInfoService) {
        this.userAddressMapper = userAddressMapper;
        this.identityService = identityService;
    }

    // 获取用户地址 ID
    @Override
    public UserAddress getAddress(String id) throws Exception {
        UserAddress userAddress = userAddressMapper.getAddress(id);
        if (userAddress!=null) {
            return userAddressMapper.getAddress(id);
        } else {
            throw new Exception("用户ID不存在或用户未设置地址");
        }
    }

    @Override
    public UserAddress setAddress(String province, String city, String fullAddress) throws Exception {
        final String uuid = String.valueOf(UUID.randomUUID());
        userAddressMapper.setAddress(uuid, province, city, fullAddress);
        return getAddress(uuid);
    }

    @Override
    public UserAddress updateAddress(HttpServletRequest request, String addressId, String province, String city, String fullAddress) throws Exception {
        String userId = userAddressMapper.getUserIdByAddressId(addressId);
        if (identityService.check(request, userId, IdentityService.ById)) {
            userAddressMapper.updateAddress(addressId, province, city, fullAddress);
            return userAddressMapper.getAddress(addressId);
        } else {
            return null;
        }
    }

    @Override
    public void deleteAddress(String id) {
        userAddressMapper.deleteAddress(id);
    }

    @Override
    public String getUserIdByAddressId(String addressId) {
        return userAddressMapper.getUserIdByAddressId(addressId);
    }
}
