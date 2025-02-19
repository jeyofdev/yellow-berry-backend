package com.jeyofdev.yellow_berry.domain.brand;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;
}
