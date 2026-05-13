package com.example.yukla.controller;

import com.example.yukla.dto.CityResponse;
import com.example.yukla.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<CityResponse>> getCitiesByRegion(@PathVariable Integer regionId) {
        return ResponseEntity.ok(cityService.getCitiesByRegion(regionId));
    }
}
