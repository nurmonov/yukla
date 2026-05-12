package com.example.yukla.dto;


import com.example.yukla.entity.enums.LoadStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class LoadResponse {
    private Long id;
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private CategoryResponse category;
    private BigDecimal weight;
    private String fromDistrictName;
    private String fromAddress;
    private String toDistrictName;
    private String toAddress;
    private LocalDate loadingDate;
    private Long price;
    private LoadStatus status;
    private List<String> imageUrls;
    private String description;
    private Integer viewsCount;

    public BigDecimal volume;
}