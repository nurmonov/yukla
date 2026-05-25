package com.example.yukla.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "regions")
@Getter
@Setter
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String nameUz;

    private String nameRu;
    private String nameEn;

    @Column(nullable = false, length = 2)
    private String countryCode;

    @CreationTimestamp
    private LocalDateTime createdAt;
}