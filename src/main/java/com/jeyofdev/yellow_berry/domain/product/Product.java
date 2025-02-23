package com.jeyofdev.yellow_berry.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.core.enums.StockEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.domain.brand.Brand;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.comment.Comment;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetails;
import com.jeyofdev.yellow_berry.domain.productInformation.ProductInformation;
import com.jeyofdev.yellow_berry.domain.tag.Tag;
import com.jeyofdev.yellow_berry.domain.wishlist.WishList;
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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private List<Category> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> commentList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_details_id", referencedColumnName = "id")
    @JsonIgnore
    private ProductDetails productDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_information_id", referencedColumnName = "id")
    @JsonIgnore
    private ProductInformation productInformation;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "product_wishlist",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "wishlist_id")
    )
    @JsonIgnore
    private List<WishList> wishlists = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id")
    )
    @JsonIgnore
    private List<Cart> cartList = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @JsonIgnore
    private Brand brand;
}
