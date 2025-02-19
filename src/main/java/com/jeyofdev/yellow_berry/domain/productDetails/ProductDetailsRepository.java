package com.jeyofdev.yellow_berry.domain.productDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, UUID> {
    @NonNull
    Optional<ProductDetails> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);
}
