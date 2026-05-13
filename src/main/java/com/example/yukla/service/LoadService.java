package com.example.yukla.service;

import com.example.yukla.*;
import com.example.yukla.dto.LoadCreateRequest;
import com.example.yukla.dto.LoadResponse;
import com.example.yukla.entity.Load;
import com.example.yukla.entity.User;
import com.example.yukla.entity.enums.LoadStatus;
import com.example.yukla.mapper.LoadMapper;
import com.example.yukla.repository.DistrictRepository;
import com.example.yukla.repository.LoadRepository;
import com.example.yukla.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LoadService {

    private final LoadRepository loadRepository;
    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;
    private final LoadMapper loadMapper;

    public LoadResponse createLoad(LoadCreateRequest request, String phone) {
        User shipper = (User) userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        Load load = loadMapper.toEntity(request);
        load.setShipper(shipper);
        load.setStatus(LoadStatus.ACTIVE);

        Load savedLoad = loadRepository.save(load);
        return loadMapper.toResponse(savedLoad);
    }

    public List<LoadResponse> getAllActiveLoads() {
        List<Load> loads = loadRepository.findByStatus(LoadStatus.ACTIVE);
        return loadMapper.toResponseList(loads);
    }

    public List<LoadResponse> searchLoads(Long fromDistrictId, Long toDistrictId) {
        // Murakkab qidiruv logikasi
        List<Load> loads = loadRepository.searchLoads(fromDistrictId, toDistrictId);
        return loadMapper.toResponseList(loads);
    }

    public LoadResponse getLoadDetail(Long loadId) {
        Load load = loadRepository.findById(Math.toIntExact(loadId))
                .orElseThrow(() -> new RuntimeException("Yuk topilmadi"));
        return loadMapper.toResponse(load);
    }
}
