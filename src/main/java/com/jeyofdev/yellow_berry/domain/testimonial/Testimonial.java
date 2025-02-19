package com.jeyofdev.yellow_berry.domain.testimonial;

import com.jeyofdev.yellow_berry.core.enums.JobEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "testimonial")
public class Testimonial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "firstname", columnDefinition = "VARCHAR(30)")
    private String firstname;

    @Column(name = "lastname", columnDefinition = "VARCHAR(80)")
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "job", columnDefinition = "VARCHAR(30)")
    private JobEnum job;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
}
