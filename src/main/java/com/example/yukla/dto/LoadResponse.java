package com.example.yukla.dto;

import com.example.yukla.entity.enums.LoadStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class LoadResponse {
    private Integer id;

    private Integer shipperId;
    private String shipperName;
    private String shipperPhone;

    private String titleUz;
    private String titleRu;
    private String titleEn;

    private Integer categoryId;
    private String categoryName;

    private BigDecimal weight;
    private BigDecimal volume;

    private Integer fromDistrictId;
    private String fromDistrictName;
    private String fromAddress;

    private Integer toDistrictId;
    private String toDistrictName;
    private String toAddress;

    private LocalDate loadingDate;
    private LocalTime loadingTime;

    private Long price;
    private LoadStatus status;
    private String description;
    private List<String> imageUrls;

    private Integer viewsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}