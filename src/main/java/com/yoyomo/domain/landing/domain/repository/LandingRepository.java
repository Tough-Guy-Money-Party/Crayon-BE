package com.yoyomo.domain.landing.domain.repository;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.landing.domain.entity.Landing;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LandingRepository extends JpaRepository<Landing, UUID> {
    Optional<Landing> findByClub(Club club);
}
