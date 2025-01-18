package com.codigo.microservices.api.gateway.mapper;

import com.codigo.microservices.api.gateway.dto.UserDto;
import com.codigo.microservices.api.gateway.entity.User;

public class UserMapper {
    public static User toEntity(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }
}
