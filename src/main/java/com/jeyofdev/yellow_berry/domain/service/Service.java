package com.jeyofdev.yellow_berry.domain.service;

import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
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
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    @NotNull(message = ErrorMessage.REQUIRED_NAME)
    @Size(min = 3, max = 50, message = ErrorMessage.VALID_NAME)
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(200)")
    @NotNull(message = ErrorMessage.REQUIRED_DESCRIPTION)
    @Size(min = 3, max = 200, message = ErrorMessage.VALID_DESCRIPTION)
    private String description;
}
