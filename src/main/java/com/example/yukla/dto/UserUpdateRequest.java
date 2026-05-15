package com.example.yukla.dto;


import com.example.yukla.entity.enums.UserType;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class UserUpdateRequest {

    private String phone;           // Endi o'zgartirish mumkin
    private String password;        // Yangi parol (ixtiyoriy)
    private String firstName;
    private String lastName;
    private String firstNameRu;
    private String lastNameRu;
    private String firstNameEn;
    private String lastNameEn;
    private String displayName;
    private UserType userType;
    private BigDecimal rating;
    private Integer totalTrips;
    private String avatarUrl;
    private Boolean enabled;
}
