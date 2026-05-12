package com.example.yukla.dto;

import lombok.Data;

@Data
public class DistrictResponse {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String cityName;
    private String regionName;
}