package com.yoyomo.domain.user.domain.service;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdateService {
    private final ManagerRepository managerRepository;

    public Void updateByEmail(String email) {
        return null;
    }

    public Void deleteByEmail(String email) {
        return null;
    }

    public void addClub(Manager manager, Club club) {
        List<Club> clubs = manager.getClubs();
        clubs.add(club);
        managerRepository.save(manager);
    }

    public void deleteClub(Manager manager, Club club) {
        List<Club> clubs = manager.getClubs();
        clubs.removeIf(c -> c.getId().equals(club.getId()));
        managerRepository.save(manager);
    }
}
