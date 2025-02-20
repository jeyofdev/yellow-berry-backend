package com.jeyofdev.yellow_berry.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.core.enums.StockEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.domain.tag.Tag;
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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", columnDefinition = "VARCHAR(100)")
    private String name;

    @Column(name = "rating", columnDefinition = "INT")
    private Integer rating;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", columnDefinition = "DECIMAL(10, 2)")
    private Double price;

    @Column(name = "price_discount", columnDefinition = "DECIMAL(10, 2)")
    private Double priceDiscount;

    @Column(name = "discount", columnDefinition = "DECIMAL(10, 2)")
    private Double discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock", columnDefinition = "VARCHAR(30)")
    private StockEnum stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "weight", columnDefinition = "VARCHAR(30)")
    private WeightEnum weight;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonIgnore
    private List<Tag> tagList = new ArrayList<>();
}
