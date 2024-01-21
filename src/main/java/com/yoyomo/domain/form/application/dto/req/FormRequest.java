package com.yoyomo.domain.form.application.dto.req;

import java.util.List;

public record FormRequest(
        String clubId,
        String name,
        List<String> process
) {
}
