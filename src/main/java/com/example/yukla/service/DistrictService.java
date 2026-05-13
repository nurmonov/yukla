package com.example.yukla.service;

import com.example.yukla.dto.DistrictResponse;
import com.example.yukla.mapper.DistrictMapper;
import com.example.yukla.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    public List<DistrictResponse> getDistrictsByCity(Integer cityId) {
        return districtMapper.toResponseList(districtRepository.findByCityId(cityId));
    }
}
