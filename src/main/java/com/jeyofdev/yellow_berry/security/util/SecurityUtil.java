package com.jeyofdev.yellow_berry.security.util;

import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {
    private SecurityUtil() {
    }

    public static String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static String getAuthenticatedRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
    }

    public static void checkAuthenticatedUserOrAdminIsAuthorized(String username, boolean isAdmin) throws AccessDeniedException {
        String authenticatedUsername = getAuthenticatedUsername();
        String authenticatedRole = getAuthenticatedRole();

        boolean isAuthorized = isAdmin
                ? authenticatedUsername.equals(username) || "[ROLE_ADMIN]".equals(authenticatedRole)
                : authenticatedUsername.equals(username);

        if (!isAuthorized) {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }
}
