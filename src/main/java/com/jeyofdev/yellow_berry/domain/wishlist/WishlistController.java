package com.jeyofdev.yellow_berry.domain.wishlist;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.SaveWishlistDTO;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.WishlistDTO;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.WishlistPreviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final WishListMapper wishlistMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<WishlistPreviewDTO>>> findAllWishlists() {
        List<WishList> tagList = wishlistService.findAll();
        List<WishlistPreviewDTO> wishlistPreviewDTOs = tagList.stream().map(wishlistMapper::mapFromEntityPreview).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, wishlistPreviewDTOs);
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<DomainSuccessResponse<WishlistDTO>> findWishlistById(@PathVariable("wishlistId") UUID wishlistId) {
        WishList wishlist = wishlistService.findById(wishlistId);
        WishlistDTO wishListDTO = wishlistMapper.mapFromEntity(wishlist);

        return DomainSuccessResponse.get(HttpStatus.OK, wishListDTO);
    }

    @PostMapping("/profile/{profileId}")
    public ResponseEntity<DomainSuccessResponse<WishlistDTO>> saveWishlist(
            @PathVariable("profileId") UUID profileId,
            @RequestBody SaveWishlistDTO saveWishListDTO
    ) {
        WishList wishlist = wishlistMapper.mapToEntity(saveWishListDTO);
        WishList newWishlist = wishlistService.save(profileId, wishlist);
        WishlistDTO newWishlistDTO = wishlistMapper.mapFromEntity(newWishlist);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newWishlistDTO);
    }

    @PutMapping("/{wishlistId}")
    public ResponseEntity<DomainSuccessResponse<WishlistDTO>> updateWishlistById(
            @PathVariable("wishlistId") UUID wishlistId,
            @RequestBody SaveWishlistDTO saveWishListDTO
    ) {
        WishList wishlist = wishlistMapper.mapToEntity(saveWishListDTO);
        WishList updateWishlist = wishlistService.updateById(wishlistId, wishlist);
        WishlistDTO updateWishlistDTO = wishlistMapper.mapFromEntity(updateWishlist);

        return DomainSuccessResponse.get(HttpStatus.OK, updateWishlistDTO);
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteWishlistById(@PathVariable("wishlistId") UUID wishlistId) {
        String message = wishlistService.deleteById(wishlistId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);
    }
}
