package com.yoyomo.domain.application.domain.repository;

import com.yoyomo.domain.application.domain.entity.Application;
import com.yoyomo.domain.recruitment.domain.entity.Process;
import com.yoyomo.domain.recruitment.domain.entity.Recruitment;
import com.yoyomo.domain.user.domain.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    List<Application> findAllByUserAndDeletedAtIsNull(User user);

    List<Application> findByProcessIdAndDeletedAtIsNull(Long processId, Pageable pageable);

    Page<Application> findAllByUser_NameAndProcess_RecruitmentAndDeletedAtIsNull(String name, Recruitment recruitment,
                                                                                 Pageable pageable);

    Optional<Application> findByIdAndDeletedAtIsNull(UUID id);

    List<Application> findAllByProcess(Process process);

    Page<Application> findAllByProcessAndDeletedAtIsNull(Process process, Pageable pageable);
}
