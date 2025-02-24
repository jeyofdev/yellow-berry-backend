package com.jeyofdev.yellow_berry.domain.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.auth_user.AuthUser;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.comment.Comment;
import com.jeyofdev.yellow_berry.domain.testimonial.Testimonial;
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
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "firstname", columnDefinition = "VARCHAR(30)")
    private String firstname;

    @Column(name = "lastname", columnDefinition = "VARCHAR(80)")
    private String lastname;

    @Column(name = "phone", columnDefinition = "VARCHAR(19)")
    private String phone;

    @Column(name = "address", columnDefinition = "VARCHAR(255)")
    private String address;

    @Column(name = "region", columnDefinition = "VARCHAR(30)")
    private String region;

    @Column(name = "department", columnDefinition = "VARCHAR(30)")
    private String department;

    @Column(name = "zipCode", columnDefinition = "VARCHAR(5)")
    private String zipCode;

    @Column(name = "city", columnDefinition = "VARCHAR(30)")
    private String city;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private AuthUser user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wishlist_id", referencedColumnName = "id")
    @JsonIgnore
    private WishList wishlist;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> commentList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonIgnore
    private Cart cart;
}
