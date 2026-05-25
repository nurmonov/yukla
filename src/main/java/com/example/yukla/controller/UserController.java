package com.example.yukla.controller;

import com.example.yukla.dto.ChangeRoleRequest;
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
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Barcha foydalanuvchilar (admin uchun)
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/reload")
    public ResponseEntity<User> reload(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.getCurrentUser(currentUser));
    }

    // Sahifalangan holda olish (admin uchun)
    @GetMapping("/page")
    public ResponseEntity<Page<UserResponse>> getAllUsersPaginated(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsersPaginated(pageable));
    }

    // ==================== UPDATE ====================
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Integer id,
            @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal User currentUser) {

        UserResponse response = userService.updateUser(id, request, currentUser);
        return ResponseEntity.ok(response);
    }


    // ==================== DELETE ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Integer id,
            @AuthenticationPrincipal User currentUser) {

        userService.deleteUser(id, currentUser);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> patchUser(
            @PathVariable Integer id,
            @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.patchUser(id, request, currentUser));
    }

    // ==================== ROLE / USERTYPE ALMASHTIRISH ====================
    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResponse> changeUserRole(
            @PathVariable Integer id,
            @RequestBody ChangeRoleRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.changeUserRole(id, request, currentUser));
    }
    // ==================== Qo'shimcha metodlar ====================

    // Telefon raqami bo'yicha tekshirish
    @GetMapping("/exists/{phone}")
    public ResponseEntity<Boolean> existsByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(userService.existsByPhone(phone));
    }
}