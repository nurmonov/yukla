package com.example.yukla.dto;


import lombok.Data;

@Data
public class CityResponse {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String region;
}
