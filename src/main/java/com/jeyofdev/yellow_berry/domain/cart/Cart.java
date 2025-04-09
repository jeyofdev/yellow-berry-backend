package com.jeyofdev.yellow_berry.domain.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.domain.productToCart.ProductToCart;
import com.jeyofdev.yellow_berry.domain.profile.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "sub_total_price", columnDefinition = "DECIMAL(10, 2)")
    @NotNull(message = ErrorMessage.REQUIRED_SUB_TOTAL_PRICE)
    @Min(value = 0, message = ErrorMessage.MIN_SUB_TOTAL_PRICE)
    private Double subTotalPrice;

    @Column(name = "total_price", columnDefinition = "DECIMAL(10, 2)")
    @NotNull(message = ErrorMessage.REQUIRED_TOTAL_PRICE)
    @Min(value = 0, message = ErrorMessage.MIN_TOTAL_PRICE)
    private Double totalPrice;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false)
    private Date createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private Date updatedAt;

    @OneToOne(mappedBy = "cart", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    @NotNull(message = ErrorMessage.PROFILE_NOT_NULL_CART)
    private Profile profile;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductToCart> productToCartList = new ArrayList<>();

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", date='" + getCreatedAt() + '\'' +
                ", products='" + getProductToCartList() + '\'' +

                '}';
    }
}
