package com.yoyomo.domain.club.domain.repository;

import com.yoyomo.domain.club.domain.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClubRepository extends JpaRepository<Club, UUID> {
}
