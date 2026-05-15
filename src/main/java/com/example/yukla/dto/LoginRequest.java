package com.example.yukla.dto;


import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String password;
}
