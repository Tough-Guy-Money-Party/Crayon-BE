package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.application.exception.ManagerDeleteException;
import com.yoyomo.domain.club.domain.repository.ClubMangerRepository;
import com.yoyomo.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubManagerDeleteService {

    private final ClubMangerRepository clubMangerRepository;

    public void delete(UUID uuid, List<Long> userIds, User user) {
        if (userIds.contains(user.getId())) {
            throw new ManagerDeleteException();
        }
        clubMangerRepository.deleteAllByClubIdAndIds(uuid, userIds);
    }
}
