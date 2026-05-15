package com.example.yukla.service;

import com.example.yukla.config.Transliterator;
import com.example.yukla.dto.*;
import com.example.yukla.entity.User;
import com.example.yukla.entity.enums.UserType;
import com.example.yukla.repository.UserRepository;
import com.example.yukla.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Transliterator transliterator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Bu telefon raqami allaqachon ro‘yxatdan o‘tgan");
        }

        User user = User.builder()
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userType(request.getUserType() != null ? request.getUserType() : UserType.DRIVER)
                .enabled(true)
                .build();

        // ==================== AVTOMATIK TRANSLITERATSIYA ====================
        if (request.getFirstName() != null) {
            user.setFirstNameRu(transliterator.toCyrillic(request.getFirstName()));
            user.setFirstNameEn(request.getFirstName()); // hozircha lotincha
        }
        if (request.getLastName() != null) {
            user.setLastNameRu(transliterator.toCyrillic(request.getLastName()));
            user.setLastNameEn(request.getLastName());
        }

        user.setDisplayName(request.getFirstName() + " " + request.getLastName());

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser);

        return AuthResponse.builder()
                .token(token)
                .userId(savedUser.getId())
                .phone(savedUser.getPhone())
                .displayName(savedUser.getDisplayName())
                .userType(savedUser.getUserType())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByPhone(request.getPhone());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Telefon raqami yoki parol noto‘g‘ri");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Telefon raqami yoki parol noto‘g‘ri");
        }

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .phone(user.getPhone())
                .displayName(user.getDisplayName())
                .userType(user.getUserType())
                .build();
    }
}