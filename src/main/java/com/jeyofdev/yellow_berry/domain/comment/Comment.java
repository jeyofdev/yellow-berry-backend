package com.jeyofdev.yellow_berry.domain.comment;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "firstname", columnDefinition = "VARCHAR(30)")
    private String firstname;

    @Column(name = "lastname", columnDefinition = "VARCHAR(80)")
    private String lastname;

    @Column(name = "rating", columnDefinition = "INT")
    private Integer rating;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;
}
