package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;
import com.yoyomo.domain.club.application.dto.res.ClubResponse;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubSaveService;
import com.yoyomo.domain.club.domain.service.ClubUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubManageUseCase {
    private final ClubGetService clubGetService;
    private final ClubSaveService clubSaveService;
    private final ClubUpdateService updateService;

    public ClubResponse read(String id) {
        Club club = clubGetService.byId(id);
        return new ClubResponse(club.getName(), club.getDescription());
    }

    public String create(ClubRequest request) {
        Club club = Club.builder()
                .name(request.name())
                .subDomain(request.subDomain())
                .description(request.description())
                .build();
        return clubSaveService.save(club).getId();
    }

    public void update(String id, ClubRequest request) {
        updateService.from(id, request);
    }

    public void delete(String id) {
        updateService.delete(id);
    }
}
