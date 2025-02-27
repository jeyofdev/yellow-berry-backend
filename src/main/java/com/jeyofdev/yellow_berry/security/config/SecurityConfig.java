package com.jeyofdev.yellow_berry.security.config;

import com.jeyofdev.yellow_berry.core.enums.RoleEnum;
import com.jeyofdev.yellow_berry.security.filter.JwtAuthenticationFilter;
import com.jeyofdev.yellow_berry.security.handler.AccessDeniedHandler;
import com.jeyofdev.yellow_berry.security.handler.JwtAuthenticationErrors;
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
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // disable CSRF
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringRequestMatchers("/**").disable())

                // Liste des routes protégées / non protégées
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/about/**",
                                "/api/v1/brand/**",
                                "/api/v1/category/**",
                                "/api/v1/faq/**",
                                "/api/v1/product/**",
                                "/api/v1/product_details/**",
                                "/api/v1/product_information/**",
                                "/api/v1/service/**",
                                "/api/v1/tag/**",
                                "/api/v1/team/member/**",
                                "/api/v1/testimonial/**",
                                "/api/v1/comment/{commentId}",
                                "/api/v1/product/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/users",
                                "/api/v1/wishlist",
                                "/api/v1/profile",
                                "/api/v1/cart",
                                "/api/v1/product",
                                "/api/v1/comment"
                        ).hasRole(RoleEnum.ADMIN.name())

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/users/email/{email}",
                                "/api/v1/users/{userId}",
                                "/api/v1/wishlist/{wishlistId}",
                                "/api/v1/profile/{profileId}",
                                "/api/v1/cart/{cartId}"
                        ).hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.USER.name())

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/auth/update-password",
                                "/api/v1/comment/product/{productId}/profile/{profileId}"
                        ).hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.USER.name())

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/about",
                                "/api/v1/brand",
                                "/api/v1/category",
                                "/api/v1/faq",
                                "/api/v1/product_details/product/{productId}",
                                "/api/v1/product_information/product/{productId}",
                                "/api/v1/service",
                                "/api/v1/tag",
                                "/api/v1/team/member",
                                "/api/v1/testimonial",
                                "/api/v1/product"
                        ).hasRole(RoleEnum.ADMIN.name())

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/wishlist/profile/{profileId}",
                                "/api/v1/profile/user{userId}",
                                "/api/v1/cart/profile/{profileId}",
                                "/api/v1/product/{productId}/add/cart/{cartId}",
                                "/api/v1/product/{productId}/remove/cart/{cartId}",
                                "/api/v1/product/{productId}/wishlist/{wishlistId}"
                        ).hasRole(RoleEnum.USER.name())

                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/about/{aboutId}",
                                "/api/v1/brand/{brandId}",
                                "/api/v1/category/{categoryId}",
                                "/api/v1/faq/{faqId}",
                                "/api/v1/product_details/{faqId}",
                                "/api/v1/product_information/{productInformationId}",
                                "/api/v1/service/{serviceId}",
                                "/api/v1/tag/{tagId}",
                                "/api/v1/team/member/{teamMemberId}",
                                "/api/v1/testimonial/{testimonialId}",
                                "/api/v1/product/{productId}"
                        ).hasRole(RoleEnum.ADMIN.name())

                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/wishlist/{wishlistId}",
                                "/api/v1/profile/{profileId}",
                                "/api/v1/cart/{cartId}"
                        ).hasRole(RoleEnum.USER.name())

                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/comment/{commentId}"
                        ).hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.USER.name())

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/brand/{brandId}",
                                "/api/v1/category/{categoryId}",
                                "/api/v1/faq/{faqId}",
                                "/api/v1/product_details/**",
                                "/api/v1/product_information/{productInformationId}",
                                "/api/v1/service/{serviceId}",
                                "/api/v1/tag/{tagId}",
                                "/api/v1/team/member/{teamMemberId}",
                                "/api/v1/testimonial/{testimonialId}",
                                "/api/v1/product/{productId}/wishlist/{wishlistId}"
                        ).hasRole(RoleEnum.ADMIN.name())

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/wishlist/{wishlistId}",
                                "/api/v1/profile/{profileId}",
                                "/api/v1/cart/{cartId}",
                                "/api/v1/comment/{commentId}"
                        ).hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.USER.name())

                        .anyRequest().authenticated()
                )

                // authentication errors
                .exceptionHandling((exception) -> exception
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
