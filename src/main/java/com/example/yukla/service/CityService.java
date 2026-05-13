package com.example.yukla.service;

import com.example.yukla.dto.CityResponse;
import com.example.yukla.mapper.CityMapper;
import com.example.yukla.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public List<CityResponse> getAllCities() {
        return cityMapper.toResponseList(cityRepository.findAll());
    }

    public List<CityResponse> getCitiesByRegion(Integer regionId) {
        return cityMapper.toResponseList(cityRepository.findByRegionId(regionId));
    }
}
