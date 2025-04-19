package com.jeyofdev.yellow_berry.domain.brand;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.annotation.ValidEnum;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.core.enums.ColorEnum;
import com.jeyofdev.yellow_berry.domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Column(name = "name", columnDefinition = "VARCHAR(30)", unique = true)
    @NotNull(message = ErrorMessage.REQUIRED_NAME)
    @Size(min = 3, max = 30, message = ErrorMessage.VALID_NAME)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", columnDefinition = "VARCHAR(255)")
    @NotNull(message = ErrorMessage.REQUIRED_COLOR)
    @ValidEnum(enumClass = ColorEnum.class, message = ErrorMessage.VALID_COLOR)
    private ColorEnum color;

    @OneToMany(mappedBy = "brand", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    private List<Product> productList = new ArrayList<>();
}
