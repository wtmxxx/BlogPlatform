package com.wotemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    private String id;
    private String province;
    private String city;
    private String fullAddress;

    // 省略 getter 和 setter 方法
}
