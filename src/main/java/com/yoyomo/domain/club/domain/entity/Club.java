package com.yoyomo.domain.club.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.yoyomo.domain.club.application.dto.request.ClubRequest.Update;
import com.yoyomo.domain.landing.application.dto.request.LandingRequest.General;
import com.yoyomo.domain.landing.domain.entity.Landing;
import com.yoyomo.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
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
public class Club extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "club_id")
	private UUID id;

	private String name;

	@Column(unique = true)
	private String subDomain;

	private String code;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "landing_id")
	private Landing landing;

	private String notionPageLink;

	private LocalDateTime deletedAt;

	@PrePersist
	public void init() {
		generateCode();
	}

	public String generateCode() {
		code = UUID.randomUUID().toString();
		return code;
	}

	public void update(Update dto) {
		this.name = dto.name();
	}

	public void update(General dto) {
		this.subDomain = dto.subDomain();
		this.notionPageLink = dto.notionPageLink();
	}

	public void update(String notionPageLink) {
		this.notionPageLink = notionPageLink;
	}

	public void delete() {
		this.deletedAt = LocalDateTime.now();
	}

	public void addSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}

	public void addLanding(Landing landing) {
		this.landing = landing;
	}

	public boolean checkExistsSubDomain() {
		return !(subDomain == null);
	}
}
