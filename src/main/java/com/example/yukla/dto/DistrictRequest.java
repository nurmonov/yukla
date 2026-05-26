package com.example.yukla.dto;

import lombok.Data;

@Data
public class DistrictRequest {
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Integer regionId;        // Qaysi viloyatga tegishli
    private Double latitude;
    private Double longitude;
}