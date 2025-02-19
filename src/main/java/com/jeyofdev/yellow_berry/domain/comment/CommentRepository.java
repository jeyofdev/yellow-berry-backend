package com.jeyofdev.yellow_berry.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @NonNull
    Optional<Comment> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);
}
