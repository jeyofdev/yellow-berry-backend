package com.jeyofdev.yellow_berry.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    // trigger automatically when user
    // try to access a resource protected
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);

        Map<String, String> error = new HashMap<>();
        error.put("access_denied", "true");
        error.put("message", ErrorMessage.NO_SUFFICIENT_RIGHT);

        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}