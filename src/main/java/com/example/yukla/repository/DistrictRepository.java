package com.example.yukla.repository;

import com.example.yukla.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
}
