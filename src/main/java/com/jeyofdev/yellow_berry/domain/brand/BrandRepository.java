package com.jeyofdev.yellow_berry.domain.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {
    @NonNull
    Optional<Brand> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);

    boolean existsByName(String name);
}
