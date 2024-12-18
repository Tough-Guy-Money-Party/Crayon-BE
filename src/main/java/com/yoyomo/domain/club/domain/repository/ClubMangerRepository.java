package com.yoyomo.domain.club.domain.repository;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.user.domain.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClubMangerRepository extends JpaRepository<ClubManager, Long> {

    Optional<ClubManager> findByClubAndManager(Club club, User manager);

    boolean existsByClubAndManager(Club club, User manager);

    @Query("SELECT cm.club FROM ClubManager cm WHERE cm.manager.id = :managerId")
    List<Club> findAllMyClubs(long managerId);

    List<ClubManager> findAllByClubId(UUID clubId);

    @Query("SELECT cm FROM ClubManager cm WHERE cm.club = :club AND cm.manager.id = :userId")
    Optional<ClubManager> findByClubAndUserId(Club club, long userId);
}
