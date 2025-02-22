package com.jeyofdev.yellow_berry.domain.brand;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "name", columnDefinition = "VARCHAR(100)")
    private String name;

    @OneToMany(mappedBy = "brand", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    private List<Product> productList = new ArrayList<>();
}
