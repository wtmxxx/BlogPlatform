package com.wotemo.mapper;

import com.wotemo.pojo.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserAddressMapper {
    @Select("select id, province, city, full_address from user_address where id=#{id}")
    public UserAddress getAddress(String id);

    public void setAddress(String id, String province, String city, String fullAddress);

    public void updateAddress(String addressId, String province, String city, String fullAddress);

    void deleteAddress(String id);

    String getUserIdByAddressId(String addressId);
}
