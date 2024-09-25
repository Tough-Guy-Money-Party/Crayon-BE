package com.yoyomo.domain.recruitment.application.mapper;

import com.yoyomo.domain.application.application.dto.response.ApplicationResponseDTO;
import com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO.*;
import static com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO.*;

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

    /*
        해당 DTO는 List<ApplicationResponseDTO.Response> applications를 지니고 있음에도 해당 메서드는 .Detail을 사용해 매핑하고 있음
        해당 로직이 왜 필요한지가 불분명해 일단 주석처리 하고 아래 새로운 메서드를 구현하였음
     */
//    @Mapping(target = "applications", source = "applications")
//    @Mapping(target = "applicantCount", expression = "java( applications.size() )")
//    Response toResponse(Process process, List<ApplicationResponseDTO.Detail> applications);

    // 지원서의 개수로만 applicationCount를 반환하면 soft delete된 개수도 반환하기 때문에 엔티티 안에서 관리하는 것으로 수정하는 것을 권장
    @Mapping(target = "applications", source = "applications")
    @Mapping(target = "applicantCount", expression = "java( applications.size() )")
    DetailResponse toResponse(Process process, List<ApplicationResponseDTO.Response> applications);
}
