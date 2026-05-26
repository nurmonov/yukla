package com.example.yukla.repository;

import com.example.yukla.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByNameUzIgnoreCase(String nameUz);
    Optional<Category> findByNameRuIgnoreCase(String nameRu);

    List<Category> findByNameUzContainingIgnoreCase(String name);

    @Query("SELECT c FROM Category c WHERE " +
            "LOWER(c.nameUz) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.nameRu) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.nameEn) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Category> searchByName(String keyword);

    boolean existsByNameUzIgnoreCase(String nameUz);

    List<Category> findAllByOrderByNameUzAsc();
}
