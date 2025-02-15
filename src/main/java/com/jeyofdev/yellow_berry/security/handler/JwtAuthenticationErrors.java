package com.jeyofdev.yellow_berry.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeyofdev.yellow_berry.util.Helper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationErrors implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        Map<String, String> error = new HashMap<>();

        response.setStatus(UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setHeader("error", exception.getMessage());

        if (request.getAttribute("expired_exception") != null) {
            error.put("message", "JWT has expired. Please log in again.");
            error.put("status", "401");
            error.put("exceptionName", "expired_exception");
            error.put("date", Helper.simpleDateFormat());
        }
        else if (request.getAttribute("malformed_exception") != null) {
            error.put("message", "JWT is malformed. Please verify its integrity.");
            error.put("status", "401");
            error.put("exceptionName", "malformed_exception");
            error.put("date", Helper.simpleDateFormat());
        }
        else if (request.getAttribute("jwt_exception") != null) {
            error.put("message", "is_jwt_exception.");
            error.put("status", "401");
            error.put("exceptionName", "jwt_exception");
            error.put("date", Helper.simpleDateFormat());
        }
        else if (request.getAttribute("no_jwt_provided") != null) {
            error.put("message", "A jwt token must be provided.");
            error.put("status", "401");
            error.put("exceptionName", "no_jwt_provided");
            error.put("date", Helper.simpleDateFormat());
        }

        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}