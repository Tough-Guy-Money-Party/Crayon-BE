package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.Response;
import com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO;

import java.util.List;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import static com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO.*;

public interface ClubManageUseCase {

    Response save(Save dto, Long userId);

    Response read(String clubId);

    void update(String clubId, Save dto);

    void delete(String clubId);

    List<ManagerInfo> getManagers(String clubId);
}
