package com.jeyofdev.yellow_berry.domain.about;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AboutRepository extends JpaRepository<About, UUID> {
    @NonNull
    Optional<About> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);
}
