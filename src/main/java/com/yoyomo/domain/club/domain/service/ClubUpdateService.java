package com.yoyomo.domain.club.domain.service;

import com.yoyomo.domain.club.application.dto.req.ClubRequest;

public interface ClubUpdateService {
    String ID = "id";
    String DELETED_AT = "deletedAt";

    void from(String id, ClubRequest request);

    void delete(String id);
}
