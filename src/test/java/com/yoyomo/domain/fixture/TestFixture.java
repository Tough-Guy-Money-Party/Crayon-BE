package com.yoyomo.domain.fixture;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.application.domain.entity.InterviewRecord;
import com.yoyomo.domain.application.domain.entity.ProcessResult;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.form.domain.entity.Form;
import com.yoyomo.domain.item.domain.entity.Answer;
import com.yoyomo.domain.item.domain.entity.Date;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.recruitment.domain.entity.enums.Type;
import com.yoyomo.domain.user.domain.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static Recruitment recruitment(Club club) {
        return Recruitment.builder()
                .title("모집")
                .isActive(false)
                .startAt(LocalDateTime.now().minusHours(1))
                .endAt(LocalDateTime.now().plusDays(1))
                .currentProcess(Type.FORM)
                .club(club)
                .processes(new ArrayList<>())
                .build();
    }

    public static Recruitment recruitment(Club club, Process... processes) {
        return Recruitment.builder()
                .title("모집")
                .isActive(false)
                .startAt(LocalDateTime.now().minusHours(1))
                .endAt(LocalDateTime.now().plusDays(1))
                .currentProcess(Type.FORM)
                .club(club)
                .processes(Arrays.stream(processes).toList())
                .build();
    }

    public static Application application(User user) {
        return Application.builder()
                .user(user)
                .recruitmentId(UUID.randomUUID())
                .build();
    }

    public static Application application(User user, Process process) {
        return Application.builder()
                .user(user)
                .process(process)
                .recruitmentId(UUID.randomUUID())
                .build();
    }

    public static Process process() {
        return Process.builder()
                .build();
    }

    public static Process process(int stage) {
        return Process.builder()
                .stage(stage)
                .build();
    }

    public static ProcessResult processResult(UUID applicationId, long processId, Status status) {
        return ProcessResult.builder()
                .applicationId(applicationId)
                .processId(processId)
                .status(status)
                .build();
    }

    public static Date dateItem() {
        return Date.builder()
                .title("날짜를 선택해주세요")
                .type(com.yoyomo.domain.item.domain.entity.type.Type.CALENDAR)
                .order(0)
                .required(false)
                .build();
    }

    public static Answer shortFormItem() {
        return Answer.builder()
                .title("단답형으로 입력해주세요")
                .type(com.yoyomo.domain.item.domain.entity.type.Type.SHORT_FORM)
                .order(1)
                .required(false)
                .build();
    }

    public static Form form(String clubId) {
        return Form.builder()
                .clubId(clubId)
                .title("폼 제목입니다")
                .build();
    }
}
