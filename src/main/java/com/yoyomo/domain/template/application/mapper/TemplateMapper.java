package com.yoyomo.domain.template.application.mapper;

import com.yoyomo.domain.template.application.dto.req.TemplateRequest;
import com.yoyomo.domain.template.application.dto.req.TemplateUpdateRequest;
import com.yoyomo.domain.template.application.dto.res.TemplateResponse;
import com.yoyomo.domain.template.domain.entity.Template;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TemplateMapper {
    Template from(TemplateRequest request);

    Template from(TemplateUpdateRequest request);

    TemplateResponse mapToTemplateResponse(Template template);
}