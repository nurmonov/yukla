package com.example.yukla.service;

import com.example.yukla.dto.DistrictRequest;
import com.example.yukla.dto.DistrictResponse;
import com.example.yukla.entity.District;
import com.example.yukla.entity.Region;
import com.example.yukla.mapper.DistrictMapper;
import com.example.yukla.repository.DistrictRepository;
import com.example.yukla.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;
    private final DistrictMapper districtMapper;

    // ==================== CREATE ====================
    public DistrictResponse createDistrict(DistrictRequest request) {
        // Region mavjudligini tekshirish
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new RuntimeException("Region topilmadi. ID: " + request.getRegionId()));

        // Unikal nom tekshirish
        if (districtRepository.existsByNameUzIgnoreCaseAndRegionId(request.getNameUz(), request.getRegionId())) {
            throw new RuntimeException("Bu nomdagi tuman ushbu regionda allaqachon mavjud");
        }

        District district = districtMapper.toEntity(request);
        district.setRegion(region);                    // Regionni qo'lda bog'laymiz

        District saved = districtRepository.save(district);
        return districtMapper.toResponse(saved);
    }

    // ==================== READ ====================
    public DistrictResponse getDistrictById(Integer id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tuman topilmadi. ID: " + id));
        return districtMapper.toResponse(district);
    }

    public List<DistrictResponse> getAllDistricts() {
        return districtRepository.findAll().stream()
                .map(districtMapper::toResponse)
                .toList();
    }

    public List<DistrictResponse> getDistrictsByRegion(Integer regionId) {
        return districtRepository.findByRegionId(regionId).stream()
                .map(districtMapper::toResponse)
                .toList();
    }

    public List<DistrictResponse> searchDistricts(String keyword) {
        return districtRepository.searchByName(keyword).stream()
                .map(districtMapper::toResponse)
                .toList();
    }

    // ==================== UPDATE ====================
    public DistrictResponse updateDistrict(Integer id, DistrictRequest request) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tuman topilmadi. ID: " + id));

        districtMapper.updateEntity(request, district);

        // Agar region o'zgartirilsa
        if (request.getRegionId() != null && !request.getRegionId().equals(district.getRegion().getId())) {
            Region newRegion = regionRepository.findById(request.getRegionId())
                    .orElseThrow(() -> new RuntimeException("Yangi region topilmadi"));
            district.setRegion(newRegion);
        }

        District saved = districtRepository.save(district);
        return districtMapper.toResponse(saved);
    }

    // ==================== DELETE ====================
    public void deleteDistrict(Integer id) {
        if (!districtRepository.existsById(id)) {
            throw new RuntimeException("Tuman topilmadi. ID: " + id);
        }
        districtRepository.deleteById(id);
    }
}
