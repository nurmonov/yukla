package com.example.yukla.repository;

import com.example.yukla.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {

    List<District> findByRegionId(Integer regionId);

    Optional<District> findByNameUzIgnoreCase(String nameUz);

    List<District> findByNameUzContainingIgnoreCase(String name);

    // Region bo'yicha va nom bo'yicha qidiruv
    List<District> findByRegionIdAndNameUzContainingIgnoreCase(Integer regionId, String name);

    // Umumiy qidiruv
    @Query("SELECT d FROM District d WHERE " +
            "LOWER(d.nameUz) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.nameRu) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<District> searchByName(String keyword);

    boolean existsByNameUzIgnoreCaseAndRegionId(String nameUz, Integer regionId);

    List<District> findByRegionIdOrderByNameUzAsc(Integer regionId);
}
