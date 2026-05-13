package com.example.yukla.controller;

import com.example.yukla.dto.LoadCreateRequest;
import com.example.yukla.dto.LoadResponse;
import com.example.yukla.entity.User;
import com.example.yukla.service.LoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loads")
@RequiredArgsConstructor
public class LoadController {

    private final LoadService loadService;

    @PostMapping
    public ResponseEntity<LoadResponse> createLoad(
            @RequestBody LoadCreateRequest request,
            @AuthenticationPrincipal User shipper) {

        LoadResponse response = loadService.createLoad(request, shipper.getPhone());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LoadResponse>> getAllLoads() {
        return ResponseEntity.ok(loadService.getAllActiveLoads());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoadResponse> getLoadDetail(@PathVariable Long id) {
        return ResponseEntity.ok(loadService.getLoadDetail(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<LoadResponse>> searchLoads(
            @RequestParam(required = false) Long fromDistrictId,
            @RequestParam(required = false) Long toDistrictId
           ) {

        return ResponseEntity.ok(loadService.searchLoads(fromDistrictId, toDistrictId));
    }
}