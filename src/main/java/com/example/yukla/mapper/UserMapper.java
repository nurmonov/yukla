package com.example.yukla.mapper;

import com.example.yukla.dto.UserCreateRequest;
import com.example.yukla.dto.UserResponse;
import com.example.yukla.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateRequest request);

    @Named("basicUser")
    UserResponse toResponse(User user);

    @Named("displayNameUser")
    @Mapping(target = "displayName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    UserResponse toResponseWithDisplayName(User user);

    @IterableMapping(qualifiedByName = "basicUser")
    List<UserResponse> toResponseList(List<User> users);
}
