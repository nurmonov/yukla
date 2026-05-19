package com.example.yukla.service;

import com.example.yukla.dto.*;
import com.example.yukla.entity.User;
import com.example.yukla.entity.enums.UserType;
import com.example.yukla.mapper.UserMapper;
import com.example.yukla.repository.UserRepository;
import com.example.yukla.config.Transliterator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Transliterator transliterator;
    private final PasswordEncoder passwordEncoder;

    // ==================== CREATE ====================
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Bu telefon raqami allaqachon mavjud");
        }

        User user = userMapper.toEntity(request);

        // Parolni hash qilish
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Avtomatik transliteratsiya
        applyTransliteration(user);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    // ==================== READ ====================
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));
        return userMapper.toResponse(user);
    }

    public User getCurrentUser(User currentUser) {
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("Foydalanuvchi topilmadi"));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public Page<UserResponse> getAllUsersPaginated(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest request, User currentUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        if (!currentUser.getId().equals(id) && currentUser.getUserType() != UserType.ADMIN) {
            throw new RuntimeException("Faqat o'z ma'lumotlaringizni yangilashingiz mumkin");
        }

        userMapper.updateEntity(request, user);

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        applyTransliteration(user);     // ← Bu yerda chaqiramiz

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    public UserResponse patchUser(Long id, UserUpdateRequest request, User currentUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        if (!currentUser.getId().equals(id) && currentUser.getUserType() != UserType.ADMIN) {
            throw new RuntimeException("Faqat o'z ma'lumotlaringizni yangilashingiz mumkin");
        }

        userMapper.patchEntity(request, user);

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        applyTransliteration(user);     // ← Bu yerda ham chaqiramiz

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }


    // ==================== DELETE ====================
    public void deleteUser(Long id, User currentUser) {
        if (!currentUser.getId().equals(id) && currentUser.getUserType() != UserType.ADMIN) {
            throw new RuntimeException("Faqat o'zingizni yoki admin o'chirishi mumkin");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        userRepository.delete(user);
    }

    // ==================== YORDAMCHI METOD ====================
    private void applyTransliteration(User user) {
        transliterator.autoTranslate(user);

        // Display name ni lotincha qilamiz
        if (user.getDisplayName() == null || user.getDisplayName().isBlank()) {
            user.setDisplayName(user.getFirstNameEn() + " " + user.getLastNameEn());
        }
    }

    public UserResponse changeUserRole(Long id, ChangeRoleRequest request, User currentUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foydalanuvchi topilmadi"));

        // Faqat ADMIN o'zgartira oladi
        if (currentUser.getUserType() != UserType.ADMIN) {
            throw new RuntimeException("Foydalanuvchi rolini faqat Admin o'zgartira oladi");
        }

        user.setUserType(request.getUserType());
        User saved = userRepository.save(user);


        return userMapper.toResponse(saved);
    }

    // Qo'shimcha qulay metod
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}