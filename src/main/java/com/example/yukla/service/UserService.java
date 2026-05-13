package com.example.yukla.service;

import com.example.yukla.config.Transliterator;
import com.example.yukla.dto.UserCreateRequest;
import com.example.yukla.dto.UserResponse;
import com.example.yukla.entity.User;
import com.example.yukla.mapper.UserMapper;
import com.example.yukla.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Transliterator transliterator;   // Avval yozganimiz

    public UserResponse register(UserCreateRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Bu telefon raqami allaqachon ro'yxatdan o'tgan");
        }

        User user = userMapper.toEntity(request);

        // Ismlarni avtomatik transliteratsiya qilish
        user.setFirstNameRu(transliterator.toCyrillic(user.getFirstName()));
        user.setLastNameRu(transliterator.toCyrillic(user.getLastName()));

        user.setDisplayName(user.getFirstName() + " " + user.getLastName());

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));
        return userMapper.toResponse(user);
    }

    public User findByPhone(String phone) {
        return (User) userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));
    }
}
