package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Response;

import java.io.IOException;
import java.util.List;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.*;
import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Code;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Participation;
import static com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO.ManagerInfo;

public interface ClubManageUseCase {

    Response save(Save dto, Long userId) throws IOException;

    Response read(String clubId);

    void update(String clubId, Update dto, Long userId);

    void delete(String clubId, Long userId);

    List<ManagerInfo> getManagers(String clubId, Long userId);

    Participation participate(ClubRequestDTO.Participation dto, Long userId);

    Code readCode(String clubId, Long userId);

    Code updateCode(String clubId, Long userId);

    void deleteManagers(Delete dto, Long userId);

    List<Response> readAll(Long userId);
}
