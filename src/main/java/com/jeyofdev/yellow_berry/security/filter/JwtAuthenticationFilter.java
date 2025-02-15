package com.jeyofdev.yellow_berry.security.filter;

import com.jeyofdev.yellow_berry.security.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        // header "authorization"
        final String authHeader = request.getHeader("Authorization");

        final String jwt;
        final String userEmail;

        // check if authHeader begins with "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            request.setAttribute("no_jwt_provided", "No JWT provided");
            filterChain.doFilter(request, response);

            return;
        }

        try {
            // implement JWT filter logic
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);

            // check that the user is not null and is not already logged in
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Update the SecurityContextHolder to notify it of this new authentication
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (Exception error) {
            if (error instanceof ExpiredJwtException) {
                request.setAttribute("expired_exception", error.getMessage());
            }
            else if (error instanceof MalformedJwtException) {
                request.setAttribute("malformed_exception", error.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}