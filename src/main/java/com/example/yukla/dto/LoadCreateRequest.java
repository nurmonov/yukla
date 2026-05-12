package com.example.yukla.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoadCreateRequest {
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
    private Long price;
    private String description;
}
