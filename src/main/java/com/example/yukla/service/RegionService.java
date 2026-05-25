package com.example.yukla.service;


import com.example.yukla.dto.RegionRequest;
import com.example.yukla.dto.RegionResponse;
import com.example.yukla.entity.Region;
import com.example.yukla.mapper.RegionMapper;
import com.example.yukla.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public RegionResponse createRegion(RegionRequest request) {
        // Unikal nom tekshirish
        if (regionRepository.existsByNameUzIgnoreCase(request.getNameUz())) {
            throw new RuntimeException("Bu nomdagi region allaqachon mavjud: " + request.getNameUz());
        }

        Region region = regionMapper.toEntity(request);
        Region saved = regionRepository.save(region);
        return regionMapper.toResponse(saved);
    }

    public RegionResponse getRegionById(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region topilmadi. ID: " + id));
        return regionMapper.toResponse(region);
    }

    public List<RegionResponse> getAllRegions() {
        return regionRepository.findAllByOrderByNameUzAsc().stream()
                .map(regionMapper::toResponse)
                .toList();
    }

    public List<RegionResponse> getRegionsByCountry(String countryCode) {
        return regionRepository.findByCountryCode(countryCode).stream()
                .map(regionMapper::toResponse)
                .toList();
    }

    public List<RegionResponse> searchRegions(String keyword) {
        return regionRepository.searchByName(keyword).stream()
                .map(regionMapper::toResponse)
                .toList();
    }

    public RegionResponse updateRegion(Integer id, RegionRequest request) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region topilmadi. ID: " + id));

        regionMapper.updateEntity(request, region);
        Region saved = regionRepository.save(region);
        return regionMapper.toResponse(saved);
    }

    public void deleteRegion(Integer id) {
        if (!regionRepository.existsById(id)) {
            throw new RuntimeException("Region topilmadi. ID: " + id);
        }
        regionRepository.deleteById(id);
    }
}
