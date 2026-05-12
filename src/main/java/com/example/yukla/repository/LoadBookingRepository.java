package com.example.yukla.repository;

import com.example.yukla.entity.LoadBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoadBookingRepository extends JpaRepository<LoadBooking, Integer> {
}
