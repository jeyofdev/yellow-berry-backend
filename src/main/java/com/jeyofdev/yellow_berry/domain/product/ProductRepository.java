package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.domain.category.Category;
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

    @Query("SELECT p FROM Product p JOIN p.tagList t WHERE t = :tag")
    List<Product> findByTag(@Param("tag") Tag tag);

    @Query("SELECT p FROM Product p JOIN p.categoryList c WHERE c = :category")
    List<Product> findByCategory(@Param("category") Category category);

    @Query("SELECT p FROM Product p JOIN p.wishlists c WHERE c = :wishlist")
    List<Product> findByWishlist(@Param("wishlist") WishList wishlist);
}
