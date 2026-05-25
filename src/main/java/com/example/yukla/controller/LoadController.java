package com.example.yukla.controller;

import com.example.yukla.dto.LoadRequest;
import com.example.yukla.dto.LoadResponse;
import com.example.yukla.entity.User;
import com.example.yukla.service.LoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/loads")
@RequiredArgsConstructor
public class LoadController {

    private final LoadService loadService;

    // ==================== CREATE ====================
    @PostMapping
    public ResponseEntity<LoadResponse> createLoad(
            @RequestBody LoadRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(loadService.createLoad(request, currentUser));
    }

    // ==================== READ ====================
    @GetMapping("/{id}")
    public ResponseEntity<LoadResponse> getLoadById(@PathVariable Integer id) {
        return ResponseEntity.ok(loadService.getLoadById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<LoadResponse>> getMyLoads(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(loadService.getMyLoads(currentUser));
    }

    @GetMapping
    public ResponseEntity<List<LoadResponse>> getAllLoads() {
        return ResponseEntity.ok(loadService.getAllLoads());
    }

    // ==================== SEARCH & FILTER ====================
    @GetMapping("/search")
    public ResponseEntity<List<LoadResponse>> searchLoads(
            @RequestParam(required = false) Integer fromDistrictId,
            @RequestParam(required = false) Integer toDistrictId,
            @RequestParam(required = false) BigDecimal minWeight,
            @RequestParam(required = false) BigDecimal maxWeight,
            @RequestParam(required = false) String keyword) {

        return ResponseEntity.ok(loadService.searchLoads(
                fromDistrictId, toDistrictId, minWeight, maxWeight, keyword));
    }

    // ==================== UPDATE ====================
    @PutMapping("/{id}")
    public ResponseEntity<LoadResponse> updateLoad(
            @PathVariable Integer id,
            @RequestBody LoadRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(loadService.updateLoad(id, request, currentUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LoadResponse> patchLoad(
            @PathVariable Integer id,
            @RequestBody LoadRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(loadService.patchLoad(id, request, currentUser));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoad(
            @PathVariable Integer id,
            @AuthenticationPrincipal User currentUser) {
        loadService.deleteLoad(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    // ==================== Qo'shimcha ====================
    @PostMapping("/{id}/view")
    public ResponseEntity<Void> incrementViews(@PathVariable Integer id) {
        loadService.incrementViews(id);
        return ResponseEntity.ok().build();
    }
}