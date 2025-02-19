package com.jeyofdev.yellow_berry.domain.profile;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "firstname", columnDefinition = "VARCHAR(30)")
    private String firstname;

    @Column(name = "lastname", columnDefinition = "VARCHAR(80)")
    private String lastname;

    @Column(name = "phone", columnDefinition = "VARCHAR(19)")
    private String phone;

    @Column(name = "address", columnDefinition = "VARCHAR(255)")
    private String address;

    @Column(name = "region", columnDefinition = "VARCHAR(30)")
    private String region;

    @Column(name = "department", columnDefinition = "VARCHAR(30)")
    private String department;

    @Column(name = "zipCode", columnDefinition = "VARCHAR(5)")
    private String zipCode;

    @Column(name = "city", columnDefinition = "VARCHAR(30)")
    private String city;
}
