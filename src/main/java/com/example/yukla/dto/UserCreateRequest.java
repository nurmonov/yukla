package com.example.yukla.dto;

import com.example.yukla.entity.enums.UserType;
import lombok.Data;

@Data
public class UserCreateRequest {
    private String phone;
    private String firstName;
    private String lastName;
    private UserType userType;
}
