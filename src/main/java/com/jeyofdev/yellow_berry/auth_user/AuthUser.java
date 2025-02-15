package com.jeyofdev.yellow_berry.auth_user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "\"user\"")
public class AuthUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "email", columnDefinition = "VARCHAR(100)")
    private String email;

    @JsonIgnore
    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    private String password;

    @JsonIgnore
    @Column(name = "role", columnDefinition = "VARCHAR(20)")
    private String role;

    @JsonIgnore
    @Column(name = "is_verified", columnDefinition = "BOOLEAN")
    private boolean isVerified;

    @JsonIgnore
    @Column(name = "verification_token", columnDefinition = "VARCHAR(255)")
    private String verificationToken;

    @JsonIgnore
    @Column(name = "verification_token_expiration", columnDefinition = "TIMESTAMP")
    private LocalDateTime verificationTokenExpiration;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }
}