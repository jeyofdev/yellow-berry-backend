package com.jeyofdev.yellow_berry.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @NonNull
    Optional<Product> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);
}
