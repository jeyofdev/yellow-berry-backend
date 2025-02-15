package com.jeyofdev.yellow_berry.auth_user;

import com.jeyofdev.yellow_berry.auth_user.dto.AuthUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthUserDTO mapFromEntity(AuthUser authUser);
}
