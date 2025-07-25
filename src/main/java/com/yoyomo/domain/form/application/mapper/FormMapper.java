package com.yoyomo.domain.form.application.mapper;

import static com.yoyomo.domain.form.application.dto.response.FormResponse.*;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.yoyomo.domain.form.application.dto.request.FormRequest;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.domain.entity.Item;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FormMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	Form from(FormRequest.Save dto, List<Item> items, String clubId);

	DetailResponse toDetailResponse(Form form, List<String> recruitmentIds);

	Info toInfo(Form form);

	Response toResponse(Form form);
}
