package com.yoyomo.domain.template.application.usecase;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.template.application.dto.request.MailTemplateSaveRequest;
import com.yoyomo.domain.template.domain.service.MailTemplateSaveService;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailTemplateManageUseCase {

    private final MailTemplateSaveService mailTemplateSaveService;

    private final ClubGetService clubGetService;

    private final UserGetService userGetService;

    @Transactional
    public void save(MailTemplateSaveRequest dto, Long userId){
        String clubId = dto.clubId();
        Club club = clubGetService.find(clubId);

        Manager manager = userGetService.find(userId);
        Club.checkAuthority(club, manager);

        mailTemplateSaveService.save(dto, club);
    }
}
