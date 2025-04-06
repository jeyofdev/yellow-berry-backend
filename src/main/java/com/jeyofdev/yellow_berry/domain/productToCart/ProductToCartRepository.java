package com.jeyofdev.yellow_berry.domain.productToCart;

import com.jeyofdev.yellow_berry.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface
ProductToCartRepository extends JpaRepository<ProductToCart, UUID> {
    void deleteById(@NonNull UUID id);

    boolean existsByCartIdAndProductId(UUID cartId, UUID productId);

    @Query("SELECT p FROM ProductToCart p JOIN p.cart c WHERE c = :cart")
    List<ProductToCart> findByCart(@Param("cart") Cart cart);
}
