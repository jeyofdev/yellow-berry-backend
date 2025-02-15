package com.example.demo.security.config;

import com.example.demo.core.enums.RoleEnum;
import com.example.demo.security.filter.JwtAuthenticationFilter;
import com.example.demo.security.handler.AccessDeniedHandler;
import com.example.demo.security.handler.JwtAuthenticationErrors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationErrors jwtAuthenticationErrors;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //use the CORS configuration of our implementation
                .cors(cors -> cors.configure(http))

                // disable session management
                .sessionManagement(session -> session .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // disable CSRF
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringRequestMatchers("/**").disable())

                // Liste des routes protégées / non protégées
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )

                // authentication errors
                .exceptionHandling((exception) ->  exception
                        .authenticationEntryPoint(jwtAuthenticationErrors)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                // specify the authentication provider used
                .authenticationProvider(authenticationProvider)

                // add the JWT authentication filter
                // before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
