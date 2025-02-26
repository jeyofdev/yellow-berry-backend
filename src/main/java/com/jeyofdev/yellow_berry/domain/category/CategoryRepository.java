package com.jeyofdev.yellow_berry.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @NonNull
    Optional<Category> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);

    boolean existsByName(String name);
}
