package com.yoyomo.domain.template.domain.entity;

import java.util.UUID;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.template.application.dto.request.MailTemplateUpdateRequest;
import com.yoyomo.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class MailTemplate extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "mail_template_id")
	private UUID id;

	private String customTemplateName;

	@ManyToOne
	@JoinColumn(name = "club_id")
	private Club club;

	public void update(MailTemplateUpdateRequest dto) {
		this.customTemplateName = dto.customTemplateName();
	}
}
