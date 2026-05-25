package com.example.yukla.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class LoadRequest {
    private String titleUz;
    private String titleRu;
    private String titleEn;

    private Integer categoryId;

    private BigDecimal weight;
    private BigDecimal volume;

    private Integer fromDistrictId;
    private String fromAddress;

    private Integer toDistrictId;
    private String toAddress;

    private LocalDate loadingDate;
    private LocalTime loadingTime;

    private Long price;
    private String description;
    private List<String> imageUrls;
}