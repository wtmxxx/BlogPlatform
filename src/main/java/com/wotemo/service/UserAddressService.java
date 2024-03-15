package com.wotemo.service;

import com.wotemo.pojo.UserAddress;
import jakarta.servlet.http.HttpServletRequest;

public interface UserAddressService {
    UserAddress getAddress(String id) throws Exception;
    UserAddress setAddress(String province,String city,String fullAddress) throws Exception;
    UserAddress updateAddress(HttpServletRequest request, String id, String province, String city, String fullAddress) throws Exception;

    void deleteAddress(String id);

    String getUserIdByAddressId(String addressId);
}
