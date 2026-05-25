package com.example.yukla.repository;

import com.example.yukla.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Repository
public interface CityRepository extends JpaRepository<City, Integer> {


    List<City> findByRegionId(Integer regionId);

    Optional<City> findByNameUzIgnoreCase(String nameUz);

    Optional<City> findByNameRuIgnoreCase(String nameRu);

    // Region bo'yicha va nom bo'yicha qidiruv
    List<City> findByRegionIdAndNameUzContainingIgnoreCase(Integer regionId, String name);

    // Umumiy qidiruv (barcha shaharlar bo'yicha)
    @Query("SELECT c FROM City c WHERE LOWER(c.nameUz) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.nameRu) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<City> searchByName(String keyword);



    @Query("SELECT c FROM City c WHERE c.region.countryCode = :countryCode")
    List<City> findByCountryCode(String countryCode);

    // Eng yaqin shaharlar (latitude, longitude bo'yicha)
    List<City> findByRegionIdOrderByNameUzAsc(Integer regionId);

    boolean existsByNameUzIgnoreCaseAndRegionId(String nameUz, Integer regionId);

    // Ko'p til bo'yicha qidiruv
    @Query("SELECT c FROM City c WHERE " +
            "LOWER(c.nameUz) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.nameRu) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.nameEn) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<City> searchByNameInAllLanguages(String keyword);
}
