package com.example.yukla.dto;

import com.example.yukla.entity.enums.UserType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserResponse {
    private Long id;
    private String phone;
    private String displayName;
    private UserType userType;
    private BigDecimal rating;
    private Integer totalTrips;
    private String avatarUrl;
}
