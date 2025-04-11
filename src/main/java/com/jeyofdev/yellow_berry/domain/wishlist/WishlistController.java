package com.jeyofdev.yellow_berry.domain.wishlist;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.product.ProductMapper;
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
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<WishlistDTO>>> findAllWishlists() {
        List<WishList> wishLists = wishlistService.findAll();
        List<WishlistDTO> wishlistDTOs = wishLists.stream().map(wishlist -> wishlistMapper.mapFromEntity(wishlist, productMapper)).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, wishlistDTOs);
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<DomainSuccessResponse<WishlistDTO>> findWishlistById(@PathVariable("wishlistId") UUID wishlistId) {
        WishList wishlist = wishlistService.findById(wishlistId);
        WishlistDTO wishListDTO = wishlistMapper.mapFromEntity(wishlist, productMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, wishListDTO);
    }

    @PostMapping("/profile/{profileId}")
    public ResponseEntity<DomainSuccessResponse<WishlistPreviewDTO>> saveWishlist(
            @PathVariable("profileId") UUID profileId,
            @RequestBody SaveWishlistDTO saveWishListDTO
    ) {
        WishList wishlist = wishlistMapper.mapToEntity(saveWishListDTO);
        WishList newWishlist = wishlistService.save(profileId, wishlist);
        WishlistPreviewDTO newWishlistPreviewDTO = wishlistMapper.mapFromEntityPreview(newWishlist, productMapper);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newWishlistPreviewDTO);
    }

    @PutMapping("/{wishlistId}")
    public ResponseEntity<DomainSuccessResponse<WishlistDTO>> updateWishlistById(
            @PathVariable("wishlistId") UUID wishlistId,
            @RequestBody SaveWishlistDTO saveWishListDTO
    ) {
        WishList wishlist = wishlistMapper.mapToEntity(saveWishListDTO);
        WishList updateWishlist = wishlistService.updateById(wishlistId, wishlist);
        WishlistDTO updateWishlistDTO = wishlistMapper.mapFromEntity(updateWishlist, productMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, updateWishlistDTO);
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteWishlistById(@PathVariable("wishlistId") UUID wishlistId) {
        String message = wishlistService.deleteById(wishlistId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);
    }
}
