package com.example.yukla.repository;

import com.example.yukla.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {


    List<Region> findByCountryCode(String countryCode);

    Optional<Region> findByNameUzIgnoreCase(String nameUz);
    Optional<Region> findByNameRuIgnoreCase(String nameRu);

    // Qisman qidiruv
    List<Region> findByNameUzContainingIgnoreCase(String name);
    List<Region> findByNameRuContainingIgnoreCase(String name);

    // Umumiy qidiruv (barcha tillarda)
    @Query("SELECT r FROM Region r WHERE " +
            "LOWER(r.nameUz) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.nameRu) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.nameEn) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Region> searchByName(String keyword);

    // Mavjudlik tekshirish
    boolean existsByNameUzIgnoreCase(String nameUz);
    boolean existsByNameRuIgnoreCase(String nameRu);

    // Sortlash
    List<Region> findAllByOrderByNameUzAsc();
    List<Region> findAllByOrderByNameRuAsc();

    // Country bo'yicha sortlangan
    List<Region> findByCountryCodeOrderByNameUzAsc(String countryCode);


}
