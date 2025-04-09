package com.jeyofdev.yellow_berry.domain.productToCart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.annotation.ValidEnum;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.core.model.PriceFormat;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "product_to_cart", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cart_id", "product_id"})
})
public class ProductToCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Embedded
    private PriceFormat priceDetails;

    @Column(name = "quantity", columnDefinition = "INT")
    @Min(value = 1, message = ErrorMessage.MIN_QUANTITY)
    @Max(value = 100, message = ErrorMessage.MAX_QUANTITY)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "weight", columnDefinition = "VARCHAR(10)")
    @NotNull(message = ErrorMessage.REQUIRED_WEIGHT)
    @ValidEnum(enumClass = WeightEnum.class, message = ErrorMessage.VALID_WEIGHT)
    private WeightEnum weight;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIgnore
    private Product product;

    @Override
    public String toString() {
        return "ProductToCart{" +
                "id=" + id +
                ", quantity='" + quantity + '\'' +
                // Avoid calling authUser.toString() to prevent circular reference
                '}';
    }
}
