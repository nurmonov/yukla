package com.example.yukla.dto;


import lombok.Data;

@Data
public class RegionRequest {
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String countryCode;   // "UZ" yoki boshqa
}
