package com.jeyofdev.yellow_berry.domain.productDetails;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "product_details")
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "seller", columnDefinition = "VARCHAR(255)")
    private String seller;

    @Column(name = "service", columnDefinition = "VARCHAR(255)")
    private String service;
}
