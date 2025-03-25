package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.domain.brand.Brand;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.comment.Comment;
import com.jeyofdev.yellow_berry.domain.tag.Tag;
import com.jeyofdev.yellow_berry.domain.wishlist.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface
ProductRepository extends JpaRepository<Product, UUID> {
    @NonNull
    Optional<Product> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);

    boolean existsByName(String name);

    @Query("SELECT p FROM Product p JOIN p.tagList t WHERE t = :tag")
    List<Product> findByTag(@Param("tag") Tag tag);

    @Query("SELECT p FROM Product p JOIN p.categoryList c WHERE c = :category")
    List<Product> findByCategory(@Param("category") Category category);

    @Query("SELECT p FROM Product p JOIN p.wishlists w WHERE w = :wishlist")
    List<Product> findByWishlist(@Param("wishlist") WishList wishlist);

    @Query("SELECT p FROM Product p JOIN p.cartList c WHERE c = :cart")
    List<Product> findByCartList(@Param("cart") Cart cart);

    @Query("SELECT p FROM Product p JOIN p.brand b WHERE b = :brand")
    List<Product> findByBrand(@Param("brand") Brand brand);

    @Query("SELECT p FROM Product p JOIN p.commentList c WHERE c = :comment")
    List<Product> findByComment(@Param("comment") Comment comment);

    @Query("SELECT AVG(c.rating) FROM Product p JOIN p.commentList c WHERE p.id = :productId")
    Integer findAverageRatingByProductId(@Param("productId") UUID productId);
}
