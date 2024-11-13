package com.yoyomo.domain.template.domain.entity;

import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.template.application.dto.request.MailTemplateUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "template_id")
    private UUID id; //SES에 저장될 템플릿 이름

    String customTemplateName;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    public void update(MailTemplateUpdateRequest dto) {
        this.customTemplateName = dto.customTemplateName();
    }
}
