package com.yoyomo.domain.club.domain.repository;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.user.domain.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClubMangerRepository extends JpaRepository<ClubManager, Long> {

    Optional<ClubManager> findByClubAndManager(Club club, Manager manager);

    boolean existsByClubAndManager(Club club, Manager manager);

    @Query("SELECT cm.club FROM ClubManager cm WHERE cm.manager.id = :managerId")
    List<Club> findAllMyClubs(long managerId);

    List<ClubManager> findAllByClubId(UUID clubId);
}
