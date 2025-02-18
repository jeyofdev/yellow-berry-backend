package com.jeyofdev.yellow_berry.core.enums;

import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;

public enum RoleEnum {
    ADMIN, USER;

    public static RoleEnum fromString(String role) {
        try {
            return RoleEnum.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessage.ROLE_VALUE);
        }
    }
}
