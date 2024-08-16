package com.yoyomo.domain.club.domain.repository;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.user.domain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubMangerRepository extends JpaRepository<ClubManager, Long> {

    Optional<ClubManager> findByClubAndManager(Club club, Manager manager);
}
