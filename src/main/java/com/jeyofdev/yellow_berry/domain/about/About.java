package com.jeyofdev.yellow_berry.domain.about;

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
@Table(name = "about")
public class About {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title", columnDefinition = "VARCHAR(200)")
    @NotNull(message = ErrorMessage.REQUIRED_TITLE)
    @Size(min = 3, max = 200, message = ErrorMessage.VALID_TITLE)
    private String title;

    @Column(name = "subtitle", columnDefinition = "VARCHAR(200)")
    @NotNull(message = ErrorMessage.REQUIRED_SUBTITLE)
    @Size(min = 3, max = 200, message = ErrorMessage.VALID_SUBTITLE)
    private String subtitle;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotNull(message = ErrorMessage.REQUIRED_DESCRIPTION)
    private String description;
}
