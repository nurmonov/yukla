package com.example.yukla.repository;

import com.example.yukla.entity.Load;
import com.example.yukla.entity.enums.LoadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LoadRepository extends JpaRepository<Load, Integer> {
    List<Load> findByStatus(LoadStatus loadStatus);

    @Query("""
SELECT l FROM Load l
WHERE (:fromDistrictId IS NULL OR l.fromDistrict.id = :fromDistrictId)
AND (:toDistrictId IS NULL OR l.toDistrict.id = :toDistrictId)
""")
    List<Load> searchLoads(Long fromDistrictId, Long toDistrictId);



}
