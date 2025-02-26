package com.jeyofdev.yellow_berry.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    @NonNull
    Optional<Tag> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);

    boolean existsByName(String name);
}
