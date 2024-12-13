package com.yoyomo.fixture;

import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.user.domain.entity.User;

import java.util.UUID;

public class TestFixture {

    public static User user() {
        return User.builder()
                .name("아연")
                .email("naayen@crayon.land")
                .tel("01012345678")
                .build();
    }

    public static Club club() {
        return Club.builder()
                .name("끄래용~")
                .subDomain("yoyomo")
                .build();
    }

    public static ClubManager clubManager(Club club, User user) {
        return ClubManager.asManager(club, user);
    }

    public static InterviewRecord interviewRecord(User manager) {
        return InterviewRecord.builder()
                .applicationId(UUID.randomUUID())
                .manager(manager)
                .content("면접 태도가 좋네요")
                .build();
    }
}
