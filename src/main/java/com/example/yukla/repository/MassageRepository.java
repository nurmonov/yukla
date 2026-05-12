package com.example.yukla.repository;

import com.example.yukla.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MassageRepository extends JpaRepository<Message, Integer> {

}
