package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Response;

import java.util.List;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Code;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Participation;
import static com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO.ManagerInfo;

public interface ClubManageUseCase {

    Response save(Save dto, Long userId);

    Response read(String clubId);

    void update(String clubId, Save dto);

    void delete(String clubId);

    List<ManagerInfo> getManagers(String clubId);

    Participation participate(ClubRequestDTO.Participation dto, Long userId);

    Code readCode(String clubId, Long userId);
}
