package com.yoyomo.domain.landing.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.landing.domain.entity.Landing;

public interface LandingRepository extends JpaRepository<Landing, UUID> {
	Optional<Landing> findByClub(Club club);
}
