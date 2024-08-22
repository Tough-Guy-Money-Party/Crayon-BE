package com.yoyomo.domain.recruitment.application.usecase;

import com.yoyomo.domain.recruitment.application.dto.request.ProcessRequestDTO;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;

import java.util.List;

import static com.yoyomo.domain.recruitment.application.dto.response.ProcessResponseDTO.Response;

public interface ProcessManageUseCase {

    List<Process> save(List<ProcessRequestDTO.Save> dto, Recruitment recruitment);

    List<Response> readAll(String recruitmentId);

    List<Process> update(List<ProcessRequestDTO.Update> dto, Recruitment recruitment);
}
