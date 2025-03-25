package com.jeyofdev.yellow_berry.domain.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeyofdev.yellow_berry.auth_user.AuthUser;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.core.constant.Regex;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.comment.Comment;
import com.jeyofdev.yellow_berry.domain.wishlist.WishList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "firstname", columnDefinition = "VARCHAR(30)")
    @NotNull(message = ErrorMessage.REQUIRED_FIRSTNAME)
    @Size(min = 3, max = 30, message = ErrorMessage.VALID_FIRSTNAME)
    private String firstname;

    @Column(name = "lastname", columnDefinition = "VARCHAR(80)")
    @NotNull(message = ErrorMessage.REQUIRED_LASTNAME)
    @Size(min = 3, max = 80, message = ErrorMessage.VALID_LASTNAME)
    private String lastname;

    @Column(name = "phone", columnDefinition = "VARCHAR(19)", unique = true)
    @NotNull(message = ErrorMessage.REQUIRED_PHONE)
    @Pattern(regexp = Regex.PHONE_PATTERN, message = ErrorMessage.VALID_PHONE)
    private String phone;

    @Column(name = "address", columnDefinition = "VARCHAR(255)")
    @NotNull(message = ErrorMessage.REQUIRED_ADDRESS)
    @Size(min = 3, max = 100, message = ErrorMessage.VALID_ADDRESS)
    private String address;

    @Column(name = "zipCode", columnDefinition = "VARCHAR(5)")
    @NotNull(message = ErrorMessage.REQUIRED_ZIP_CODE)
    @Pattern(regexp = Regex.ZIPCODE_PATTERN, message = ErrorMessage.VALID_ZIP_CODE)
    private String zipCode;

    @Column(name = "city", columnDefinition = "VARCHAR(30)")
    @NotNull(message = ErrorMessage.REQUIRED_CITY)
    @Size(min = 3, max = 30, message = ErrorMessage.VALID_CITY)
    private String city;

    @Column(name = "department", columnDefinition = "VARCHAR(30)")
    @NotNull(message = ErrorMessage.REQUIRED_DEPARTMENT)
    @Size(min = 3, max = 30, message = ErrorMessage.VALID_DEPARTMENT)
    private String department;

    @Column(name = "region", columnDefinition = "VARCHAR(30)")
    @NotNull(message = ErrorMessage.REQUIRED_REGION)
    @Size(min = 3, max = 30, message = ErrorMessage.VALID_REGION)
    private String region;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    @NotNull(message = ErrorMessage.AUTH_USER_NOT_NULL_PROFILE)
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
