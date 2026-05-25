package com.example.yukla.controller;

import com.example.yukla.dto.CityRequest;
import com.example.yukla.dto.CityResponse;
import com.example.yukla.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponse> create(@RequestBody CityRequest request) {
        return ResponseEntity.ok(cityService.createCity(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAll() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<CityResponse>> getByRegion(@PathVariable Integer regionId) {
        return ResponseEntity.ok(cityService.getCitiesByRegion(regionId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CityResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(cityService.searchCities(keyword));
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<List<CityResponse>> getByCountry(@PathVariable String countryCode) {
        return ResponseEntity.ok(cityService.getCitiesByCountry(countryCode));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> update(
            @PathVariable Integer id,
            @RequestBody CityRequest request) {
        return ResponseEntity.ok(cityService.updateCity(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}