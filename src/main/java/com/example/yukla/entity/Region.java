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

    @Column(nullable = false)
    private String nameUz;

    private String nameRu;
    private String nameEn;

    private String code;   // SUR, TASH, AND va h.k.

    @CreationTimestamp
    private LocalDateTime createdAt;
}