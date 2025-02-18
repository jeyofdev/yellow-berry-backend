package com.jeyofdev.yellow_berry.domain.team_member;

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
@Table(name = "team_member")
public class TeamMember {
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

    @Column(name = "twitter", columnDefinition = "VARCHAR(80)")
    private String twitter;

    @Column(name = "instagram", columnDefinition = "VARCHAR(80)")
    private String instagram;

    @Column(name = "linkedin", columnDefinition = "VARCHAR(80)")
    private String linkedin;
}
