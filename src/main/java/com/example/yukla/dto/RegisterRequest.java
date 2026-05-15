package com.example.yukla.dto;

import com.example.yukla.entity.enums.UserType;
import lombok.Data;

@Data
public class RegisterRequest {
    private String phone;
    private String password;
    private String firstName;
    private String lastName;
    private UserType userType; // DRIVER yoki SHIPPER
}