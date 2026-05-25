package com.example.yukla.service;


import com.example.yukla.dto.CityRequest;
import com.example.yukla.dto.CityResponse;
import com.example.yukla.entity.City;
import com.example.yukla.entity.Region;
import com.example.yukla.mapper.CityMapper;
import com.example.yukla.repository.CityRepository;
import com.example.yukla.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {

    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final CityMapper cityMapper;

    public CityResponse createCity(CityRequest request) {
        // 1. Region mavjudligini tekshirish
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new RuntimeException("Region topilmadi. ID: " + request.getRegionId()));

        // 2. Unikal nom tekshirish
        if (cityRepository.existsByNameUzIgnoreCaseAndRegionId(request.getNameUz(), request.getRegionId())) {
            throw new RuntimeException("Bu nomdagi shahar allaqachon mavjud");
        }

        // 3. Entity yaratish
        City city = cityMapper.toEntity(request);

        // 4. Regionni qo'lda bog'lash (ENG MUHIM QISM!)
        city.setRegion(region);

        // 5. Saqlash
        City savedCity = cityRepository.save(city);

        return cityMapper.toResponse(savedCity);
    }

    public CityResponse getCityById(Integer id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shahar topilmadi. ID: " + id));
        return cityMapper.toResponse(city);
    }

    public List<CityResponse> getAllCities() {
        return cityRepository.findAll().stream()
                .map(cityMapper::toResponse)
                .toList();
    }

    public List<CityResponse> getCitiesByRegion(Integer regionId) {
        return cityRepository.findByRegionId(regionId).stream()
                .map(cityMapper::toResponse)
                .toList();
    }

    public List<CityResponse> getCitiesByCountry(String countryCode) {
        return cityRepository.findByCountryCode(countryCode).stream()
                .map(cityMapper::toResponse)
                .toList();
    }

    public List<CityResponse> searchCities(String keyword) {
        return cityRepository.searchByName(keyword).stream()
                .map(cityMapper::toResponse)
                .toList();
    }

    public CityResponse updateCity(Integer id, CityRequest request) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shahar topilmadi. ID: " + id));

        cityMapper.updateEntity(request, city);

        // Agar region o'zgartirilgan bo'lsa
        if (request.getRegionId() != null && !request.getRegionId().equals(city.getRegion().getId())) {
            Region newRegion = regionRepository.findById(request.getRegionId())
                    .orElseThrow(() -> new RuntimeException("Yangi region topilmadi"));
            city.setRegion(newRegion);
        }

        City saved = cityRepository.save(city);
        return cityMapper.toResponse(saved);
    }

    public void deleteCity(Integer id) {
        if (!cityRepository.existsById(id)) {
            throw new RuntimeException("Shahar topilmadi. ID: " + id);
        }
        cityRepository.deleteById(id);
    }
}
