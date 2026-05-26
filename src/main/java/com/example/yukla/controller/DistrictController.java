package com.example.yukla.controller;

import com.example.yukla.dto.DistrictRequest;
import com.example.yukla.dto.DistrictResponse;
import com.example.yukla.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    // ==================== CREATE ====================
    @PostMapping
    public ResponseEntity<DistrictResponse> createDistrict(@RequestBody DistrictRequest request) {
        return ResponseEntity.ok(districtService.createDistrict(request));
    }

    // ==================== READ ====================
    @GetMapping("/{id}")
    public ResponseEntity<DistrictResponse> getDistrictById(@PathVariable Integer id) {
        return ResponseEntity.ok(districtService.getDistrictById(id));
    }

    @GetMapping
    public ResponseEntity<List<DistrictResponse>> getAllDistricts() {
        return ResponseEntity.ok(districtService.getAllDistricts());
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<DistrictResponse>> getDistrictsByRegion(@PathVariable Integer regionId) {
        return ResponseEntity.ok(districtService.getDistrictsByRegion(regionId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DistrictResponse>> searchDistricts(@RequestParam String keyword) {
        return ResponseEntity.ok(districtService.searchDistricts(keyword));
    }

    // ==================== UPDATE ====================
    @PutMapping("/{id}")
    public ResponseEntity<DistrictResponse> updateDistrict(
            @PathVariable Integer id,
            @RequestBody DistrictRequest request) {
        return ResponseEntity.ok(districtService.updateDistrict(id, request));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Integer id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.noContent().build();
    }
}
