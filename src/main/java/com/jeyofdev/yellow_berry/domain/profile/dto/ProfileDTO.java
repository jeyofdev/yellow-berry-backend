package com.jeyofdev.yellow_berry.domain.profile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeyofdev.yellow_berry.core.model.AddressFormat;
import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import com.jeyofdev.yellow_berry.core.model.NameFormat;
import com.jeyofdev.yellow_berry.domain.comment.Comment;
import com.jeyofdev.yellow_berry.domain.wishlist.WishList;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProfileDTO(
        UUID id,
        String email,
        String role,
        NameFormat nameDetails,
        String phone,
        AddressFormat addressDetails,
        WishList wishlist,
        ListResponseFormat<Comment> comments
) {
}
