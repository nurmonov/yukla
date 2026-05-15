package com.example.yukla.repository;

import com.example.yukla.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhone(String username);

    boolean existsByPhone(String phone);

}
