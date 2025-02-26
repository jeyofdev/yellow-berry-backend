package com.jeyofdev.yellow_berry.domain.faq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FaqRepository extends JpaRepository<Faq, UUID> {
    @NonNull
    Optional<Faq> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);

    boolean existsByQuestion(String question);
}
