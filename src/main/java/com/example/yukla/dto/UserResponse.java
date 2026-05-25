package com.example.yukla.dto;


import com.example.yukla.entity.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private String phone;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
