package com.example.yukla.controller;

import com.example.yukla.dto.DistrictResponse;
import com.example.yukla.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<DistrictResponse>> getDistrictsByCity(@PathVariable Integer cityId) {
        return ResponseEntity.ok(districtService.getDistrictsByCity(cityId));
    }
}
