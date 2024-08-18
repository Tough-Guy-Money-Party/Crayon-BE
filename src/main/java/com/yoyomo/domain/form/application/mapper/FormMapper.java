package com.yoyomo.domain.form.application.mapper;

import com.yoyomo.domain.form.application.dto.req.FormRequest;
import com.yoyomo.domain.form.application.dto.req.FormRequestDTO;
import com.yoyomo.domain.form.application.dto.req.FormUpdateRequest;
import com.yoyomo.domain.form.application.dto.res.FormResponse;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.domain.entity.Item;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FormMapper {

    Form from(FormRequestDTO.Save dto, List<Item> items, String clubId);

    Form from(FormUpdateRequest request, List<Item> items);

    FormResponse mapToFormResponse(Form form);
}
