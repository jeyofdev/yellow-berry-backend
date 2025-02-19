package com.jeyofdev.yellow_berry.domain.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    @NonNull
    Optional<Profile> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);
}
