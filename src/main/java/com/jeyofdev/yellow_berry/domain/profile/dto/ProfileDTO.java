package com.jeyofdev.yellow_berry.domain.profile.dto;

import com.jeyofdev.yellow_berry.auth_user.AuthUser;
import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.comment.Comment;
import com.jeyofdev.yellow_berry.domain.wishlist.WishList;

import java.util.UUID;

public record ProfileDTO(
        UUID id,
        String firstname,
        String lastname,
        String phone,
        String address,
        String region,
        String department,
        String zipCode,
        String city,
        AuthUser user,
        WishList wishList,
        ListResponseFormat<Comment> comments,
        Cart cart
) {
}
