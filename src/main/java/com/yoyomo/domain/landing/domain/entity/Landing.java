package com.yoyomo.domain.landing.domain.entity;

import java.util.UUID;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.landing.application.dto.request.LandingRequest.General;
import com.yoyomo.domain.landing.application.dto.request.LandingRequest.Style;
import com.yoyomo.domain.landing.domain.constant.DisplayMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Landing {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "landing_id")
	private UUID id;
	private String favicon;
	private String image;
	private String siteName;
	private String callToAction;
	private String buttonColor;
	private String textColor;
	private DisplayMode displayMode = DisplayMode.LIGHT;

	@OneToOne(mappedBy = "landing")
	private Club club;

	public Landing(Club club) {
		this.club = club;
	}

	public void updateStyle(Style dto) {
		this.callToAction = dto.callToAction();
		this.buttonColor = dto.buttonColor();
		this.textColor = dto.textColor();
		this.displayMode = dto.displayMode();
	}

	public void updateGeneral(General dto) {
		this.favicon = dto.favicon();
		this.image = dto.image();
		this.siteName = dto.siteName();
	}
}
