package com.jeyofdev.yellow_berry.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    @NonNull
    Optional<Cart> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);
}
