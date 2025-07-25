package com.yoyomo.domain.landing.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.service.ClubGetService;
import com.yoyomo.domain.club.domain.service.ClubValidateService;
import com.yoyomo.domain.landing.application.dto.response.LandingResponse.All;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.infra.notion.service.NotionGetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandingAllSettingManageUsecase {

	private final ClubGetService clubGetService;
	private final NotionGetService notionGetService;
	private final ClubValidateService clubValidateService;

	public All readAll(String subDomain) {
		Club club = clubGetService.findBySubDomain(subDomain);
		Landing landing = club.getLanding();
		String parsedNotionPageLink = notionGetService.parseNotionPageLink(club.getNotionPageLink());

		return All.toAll(landing, parsedNotionPageLink);
	}

	@Transactional(readOnly = true)
	public boolean check(UUID clubId, User user) {
		Club club = clubGetService.find(clubId);
		clubValidateService.checkAuthority(club.getId(), user);

		return club.checkExistsSubDomain();
	}
}
