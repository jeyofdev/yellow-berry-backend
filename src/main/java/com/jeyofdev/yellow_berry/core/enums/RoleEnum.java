package com.jeyofdev.yellow_berry.core.enums;

public enum RoleEnum {
    ADMIN, USER;

    public static RoleEnum fromString(String role) {
        try {
            return RoleEnum.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The role must be either admin or user");
        }
    }
}
