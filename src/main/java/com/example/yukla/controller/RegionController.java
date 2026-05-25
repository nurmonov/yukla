package com.example.yukla.controller;


import com.example.yukla.dto.RegionRequest;
import com.example.yukla.dto.RegionResponse;
import com.example.yukla.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @PostMapping
    public ResponseEntity<RegionResponse> create(@RequestBody RegionRequest request) {
        return ResponseEntity.ok(regionService.createRegion(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.getRegionById(id));
    }

    @GetMapping
    public ResponseEntity<List<RegionResponse>> getAll() {
        return ResponseEntity.ok(regionService.getAllRegions());
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<List<RegionResponse>> getByCountry(@PathVariable String countryCode) {
        return ResponseEntity.ok(regionService.getRegionsByCountry(countryCode));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RegionResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(regionService.searchRegions(keyword));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionResponse> update(
            @PathVariable Integer id,
            @RequestBody RegionRequest request) {
        return ResponseEntity.ok(regionService.updateRegion(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        regionService.deleteRegion(id);
        return ResponseEntity.noContent().build();
    }
}