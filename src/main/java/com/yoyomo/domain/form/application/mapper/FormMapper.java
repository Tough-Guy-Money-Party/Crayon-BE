package com.yoyomo.domain.form.application.mapper;

import com.yoyomo.domain.form.application.dto.request.FormRequestDTO;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.domain.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static com.yoyomo.domain.form.application.dto.response.FormResponseDTO.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FormMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Form from(FormRequestDTO.Save dto, List<Item> items, String clubId);

    DetailResponse toDetailResponse(Form form);

    info toInfo(Form form);

    Response toResponse(Form form);
}
