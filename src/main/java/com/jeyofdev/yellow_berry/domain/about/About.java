package com.jeyofdev.yellow_berry.domain.about;

import jakarta.persistence.*;
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

    @Column(name = "title", columnDefinition = "VARCHAR(255)")
    private String title;

    @Column(name = "subtitle", columnDefinition = "VARCHAR(255)")
    private String subtitle;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
