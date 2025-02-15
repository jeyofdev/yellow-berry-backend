package com.example.demo.auth_user;

import com.example.demo.auth_user.dto.AuthUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthUserDTO mapFromEntity(AuthUser authUser);
}
