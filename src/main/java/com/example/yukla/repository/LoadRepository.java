package com.example.yukla.repository;

import com.example.yukla.entity.Load;
import com.example.yukla.entity.enums.LoadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LoadRepository extends JpaRepository<Load, Integer> {

    List<Load> findByShipperId(Integer shipperId);

    List<Load> findByStatus(LoadStatus status);

    List<Load> findByFromDistrictId(Integer fromDistrictId);

    List<Load> findByToDistrictId(Integer toDistrictId);

    // Eng muhim filter — yo'nalish bo'yicha
    List<Load> findByFromDistrictIdAndToDistrictId(Integer fromDistrictId, Integer toDistrictId);

    // Og'irlik oralig'i bo'yicha
    List<Load> findByWeightBetween(BigDecimal minWeight, BigDecimal maxWeight);

    // Narx bo'yicha
    List<Load> findByPriceBetween(Long minPrice, Long maxPrice);

    // Qidiruv (sarlavha bo'yicha)
    @Query("SELECT l FROM Load l WHERE " +
            "LOWER(l.titleUz) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.titleRu) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.titleEn) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Load> searchByKeyword(String keyword);

    // Foydalanuvchining faol yuklari
    List<Load> findByShipperIdAndStatus(Integer shipperId, LoadStatus status);

    // Ko'rishlar bo'yicha sortlash
    List<Load> findAllByOrderByViewsCountDesc();

    // Yangi yuklar
    List<Load> findTop10ByOrderByCreatedAtDesc();
}