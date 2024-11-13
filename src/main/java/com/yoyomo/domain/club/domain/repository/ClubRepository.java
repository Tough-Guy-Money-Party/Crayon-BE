package com.yoyomo.domain.club.domain.repository;

import com.yoyomo.domain.club.domain.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClubRepository extends JpaRepository<Club, UUID> {

    boolean existsBySubDomain(String subDomain);

    Optional<Club> findByIdAndDeletedAtIsNull(UUID id);

    Optional<Club> findByCode(String code);

    Optional<Club> findBySubDomain(String subDomain);
}
