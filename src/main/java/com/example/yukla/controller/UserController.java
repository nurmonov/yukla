package com.example.yukla.controller;

import com.example.yukla.dto.UserCreateRequest;
import com.example.yukla.dto.UserResponse;
import com.example.yukla.dto.UserUpdateRequest;
import com.example.yukla.entity.User;
import com.example.yukla.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ==================== CREATE ====================
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }

    // ==================== READ ====================

    // O'z profilini olish (eng ko'p ishlatiladigan)
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.getCurrentUser(currentUser));
    }

    // ID bo'yicha ma'lumot olish
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Barcha foydalanuvchilar (admin uchun)
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Sahifalangan holda olish (admin uchun)
    @GetMapping("/page")
    public ResponseEntity<Page<UserResponse>> getAllUsersPaginated(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsersPaginated(pageable));
    }

    // ==================== UPDATE ====================
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal User currentUser) {

        UserResponse response = userService.updateUser(id, request, currentUser);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> patchUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal User currentUser) {

        UserResponse response = userService.updateUser(id, request, currentUser); // Xuddi shu metod ishlatiladi
        return ResponseEntity.ok(response);
    }

    // ==================== DELETE ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {

        userService.deleteUser(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    // ==================== Qo'shimcha metodlar ====================

    // Telefon raqami bo'yicha tekshirish
    @GetMapping("/exists/{phone}")
    public ResponseEntity<Boolean> existsByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(userService.existsByPhone(phone));
    }
}