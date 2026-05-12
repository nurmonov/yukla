package com.example.yukla.entity;

import com.example.yukla.entity.enums.LoadStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loads")
@Getter
@Setter
public class Load {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipper_id", nullable = false)
    private User shipper;

    private String titleUz;
    private String titleRu;
    private String titleEn;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private BigDecimal weight;

    private BigDecimal volume;

    @ManyToOne
    @JoinColumn(name = "from_district_id")
    private District fromDistrict;

    private String fromAddress;

    @ManyToOne
    @JoinColumn(name = "to_district_id")
    private District toDistrict;

    private String toAddress;

    private LocalDate loadingDate;
    private LocalTime loadingTime;

    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    private LoadStatus status = LoadStatus.ACTIVE;

    @Column(columnDefinition = "TEXT[]")
    private List<String> imageUrls = new ArrayList<>();

    private String description;

    private Integer viewsCount = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}