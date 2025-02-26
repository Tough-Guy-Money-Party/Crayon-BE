package com.yoyomo.domain.mail.domain.entity;

import com.yoyomo.domain.mail.domain.entity.enums.CustomType;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonData {

    private final Map<CustomType, String> data;

    public static CommonData of(Process process, Recruitment recruitment) {
        Map<CustomType, String> data = Map.of(
                CustomType.PROCESS, process.getTitle(),
                CustomType.RECRUITMENT_NAME, recruitment.getTitle(),
                CustomType.CLUB_NAME, recruitment.getClub().getName()
        );
        return new CommonData(data);
    }
}
