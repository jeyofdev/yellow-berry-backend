package com.jeyofdev.yellow_berry.domain.testimonial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, UUID> {
    @NonNull
    Optional<Testimonial> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);
}
