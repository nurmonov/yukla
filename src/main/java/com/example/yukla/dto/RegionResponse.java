package com.example.yukla.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegionResponse {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String countryCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
