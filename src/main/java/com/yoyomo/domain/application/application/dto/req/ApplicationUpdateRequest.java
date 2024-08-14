package com.yoyomo.domain.application.application.dto.req;

import java.util.List;

public record ApplicationUpdateRequest (
        List<String> applicationIds
) { }
