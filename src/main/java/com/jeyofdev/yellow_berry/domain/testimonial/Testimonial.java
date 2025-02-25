package com.jeyofdev.yellow_berry.domain.testimonial;

import com.jeyofdev.yellow_berry.annotation.ValidEnum;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.core.enums.JobEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = ErrorMessage.REQUIRED_FIRSTNAME)
    @Size(min = 3, max = 30, message = ErrorMessage.VALID_FIRSTNAME)
    private String firstname;

    @Column(name = "lastname", columnDefinition = "VARCHAR(80)")
    @NotNull(message = ErrorMessage.REQUIRED_LASTNAME)
    @Size(min = 3, max = 80, message = ErrorMessage.VALID_LASTNAME)
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "job", columnDefinition = "VARCHAR(50)")
    @NotNull(message = ErrorMessage.REQUIRED_JOB)
    @ValidEnum(enumClass = JobEnum.class, message = ErrorMessage.VALID_JOB)
    private JobEnum job;

    @Column(name = "message", columnDefinition = "TEXT")
    @NotNull(message = ErrorMessage.REQUIRED_MESSAGE)
    private String message;
}
