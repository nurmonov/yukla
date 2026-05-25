package com.example.yukla.dto;


import com.example.yukla.entity.enums.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private Integer userId;
    private String phone;
    private String displayName;
    private UserType userType;
}
