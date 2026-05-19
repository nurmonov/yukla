package com.example.yukla.dto;


import com.example.yukla.entity.enums.UserType;
import lombok.Data;

@Data
public class ChangeRoleRequest {
    private UserType userType;
}