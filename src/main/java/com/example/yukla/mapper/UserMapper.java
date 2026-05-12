package com.example.yukla.mapper;

import com.example.yukla.dto.UserCreateRequest;
import com.example.yukla.dto.UserResponse;
import com.example.yukla.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateRequest request);

    UserResponse toResponse(User user);

    @Mapping(target = "displayName", expression = "java(user.getFirstName() + ' ' + user.getLastName())")
    UserResponse toResponseWithDisplayName(User user);

    // List uchun
    List<UserResponse> toResponseList(List<User> users);
}
