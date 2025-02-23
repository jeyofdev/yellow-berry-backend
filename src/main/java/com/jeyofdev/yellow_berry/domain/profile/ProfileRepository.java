package com.jeyofdev.yellow_berry.domain.profile;

import com.jeyofdev.yellow_berry.domain.comment.Comment;
import com.jeyofdev.yellow_berry.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    @NonNull
    Optional<Profile> findById(@NonNull UUID id);

    void deleteById(@NonNull UUID id);

    @Query("SELECT p FROM Profile p JOIN p.commentList c WHERE c = :comment")
    Profile findByComment(@Param("comment") Comment comment);
}
