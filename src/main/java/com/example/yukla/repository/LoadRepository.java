package com.example.yukla.repository;

import com.example.yukla.entity.Load;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoadRepository extends JpaRepository<Load, Integer> {
}
