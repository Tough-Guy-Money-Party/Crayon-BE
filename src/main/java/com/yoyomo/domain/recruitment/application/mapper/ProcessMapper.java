package com.yoyomo.domain.recruitment.application.mapper;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequest.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "title", source = "dto.title")
	@Mapping(target = "startAt", source = "dto.period.evaluation.time.startAt")
	@Mapping(target = "endAt", source = "dto.period.evaluation.time.endAt")
	@Mapping(target = "announceStartAt", source = "dto.period.announcement.time.startAt")
	@Mapping(target = "announceEndAt", source = "dto.period.announcement.time.endAt")
	Process from(Save dto, Recruitment recruitment);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "title", source = "dto.title")
	@Mapping(target = "startAt", source = "dto.period.evaluation.time.startAt")
	@Mapping(target = "endAt", source = "dto.period.evaluation.time.endAt")
	@Mapping(target = "announceStartAt", source = "dto.period.announcement.time.startAt")
	@Mapping(target = "announceEndAt", source = "dto.period.announcement.time.endAt")
	Process from(Update dto, Recruitment recruitment);
}
