package com.jeyofdev.yellow_berry.domain.team_member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, UUID> {
    @NonNull
    Optional<TeamMember> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);
}
