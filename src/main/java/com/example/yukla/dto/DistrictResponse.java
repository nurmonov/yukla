package com.example.yukla.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DistrictResponse {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Integer regionId;
    private String regionNameUz;     // qulaylik uchun
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
