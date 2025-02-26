package com.jeyofdev.yellow_berry.domain.faq;

import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "faq")
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "question", columnDefinition = "TEXT", unique = true)
    @NotNull(message = ErrorMessage.REQUIRED_QUESTION)
    private String question;

    @Column(name = "answer", columnDefinition = "TEXT")
    @NotNull(message = ErrorMessage.REQUIRED_ANSWER)
    private String answer;
}
