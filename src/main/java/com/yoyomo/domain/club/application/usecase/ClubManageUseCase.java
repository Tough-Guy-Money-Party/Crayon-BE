package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Response;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;

public interface ClubManageUseCase {

    Response save(Save dto, Long userId);

    Response read(String clubId);
}
