package com.example.yukla.service;

import com.example.yukla.dto.LoadRequest;
import com.example.yukla.dto.LoadResponse;
import com.example.yukla.entity.Category;
import com.example.yukla.entity.District;
import com.example.yukla.entity.Load;
import com.example.yukla.entity.User;
import com.example.yukla.entity.enums.LoadStatus;
import com.example.yukla.entity.enums.UserType;
import com.example.yukla.mapper.LoadMapper;
import com.example.yukla.repository.CategoryRepository;
import com.example.yukla.repository.DistrictRepository;
import com.example.yukla.repository.LoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LoadService {

    private final LoadRepository loadRepository;
    private final DistrictRepository districtRepository;
    private final CategoryRepository categoryRepository;
    private final LoadMapper loadMapper;

    // ==================== CREATE ====================
    public LoadResponse createLoad(LoadRequest request, User currentUser) {
        District fromDistrict = districtRepository.findById(request.getFromDistrictId())
                .orElseThrow(() -> new RuntimeException("Jo‘natish tumani topilmadi"));

        District toDistrict = districtRepository.findById(request.getToDistrictId())
                .orElseThrow(() -> new RuntimeException("Yetkazib berish tumani topilmadi"));

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Kategoriya topilmadi"));
        }

        Load load = loadMapper.toEntity(request);
        load.setShipper(currentUser);
        load.setFromDistrict(fromDistrict);
        load.setToDistrict(toDistrict);
        load.setCategory(category);
        load.setStatus(LoadStatus.ACTIVE);
        load.setViewsCount(0);

        Load saved = loadRepository.save(load);
        return loadMapper.toResponse(saved);
    }

    // ==================== READ ====================
    public LoadResponse getLoadById(Integer id) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yuk topilmadi"));
        return loadMapper.toResponse(load);
    }

    public List<LoadResponse> getMyLoads(User currentUser) {
        return loadRepository.findByShipperId(currentUser.getId()).stream()
                .map(loadMapper::toResponse)
                .toList();
    }

    public List<LoadResponse> getAllLoads() {
        return loadRepository.findAll().stream()
                .map(loadMapper::toResponse)
                .toList();
    }

    public List<LoadResponse> searchLoads(Integer fromDistrictId, Integer toDistrictId,
                                          BigDecimal minWeight, BigDecimal maxWeight, String keyword) {
        // Bu yerda murakkabroq filter logikasi qo'shsa bo'ladi
        return loadRepository.searchByKeyword(keyword != null ? keyword : "").stream()
                .map(loadMapper::toResponse)
                .toList();
    }

    // ==================== UPDATE ====================
    public LoadResponse updateLoad(Integer id, LoadRequest request, User currentUser) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yuk topilmadi"));

        if (!load.getShipper().getId().equals(currentUser.getId()) &&
                currentUser.getUserType() != UserType.ADMIN) {
            throw new RuntimeException("Bu yukni faqat egasi o'zgartira oladi");
        }

        loadMapper.updateEntity(request, load);

        // District larni yangilash
        if (request.getFromDistrictId() != null) {
            District from = districtRepository.findById(request.getFromDistrictId())
                    .orElseThrow(() -> new RuntimeException("Jo‘natish tumani topilmadi"));
            load.setFromDistrict(from);
        }
        if (request.getToDistrictId() != null) {
            District to = districtRepository.findById(request.getToDistrictId())
                    .orElseThrow(() -> new RuntimeException("Yetkazib berish tumani topilmadi"));
            load.setToDistrict(to);
        }

        Load saved = loadRepository.save(load);
        return loadMapper.toResponse(saved);
    }

    public LoadResponse patchLoad(Integer id, LoadRequest request, User currentUser) {
        // Hozircha update bilan bir xil (MapStruct IGNORE bilan)
        return updateLoad(id, request, currentUser);
    }

    // ==================== DELETE ====================
    public void deleteLoad(Integer id, User currentUser) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yuk topilmadi"));

        if (!load.getShipper().getId().equals(currentUser.getId()) &&
                currentUser.getUserType() != UserType.ADMIN) {
            throw new RuntimeException("Bu yukni faqat egasi o'chira oladi");
        }

        loadRepository.deleteById(id);
    }

    // ==================== Qo'shimcha ====================
    public void incrementViews(Integer id) {
        loadRepository.findById(id).ifPresent(load -> {
            load.setViewsCount(load.getViewsCount() + 1);
            loadRepository.save(load);
        });
    }
}