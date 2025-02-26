package com.jeyofdev.yellow_berry.domain.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<WishList, UUID> {
    @NonNull
    Optional<WishList> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);

    boolean existsByName(String name);
}
