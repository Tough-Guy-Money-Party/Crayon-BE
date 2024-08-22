package com.yoyomo.domain.recruitment.domain.repository;

import com.yoyomo.domain.recruitment.domain.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessRepository extends JpaRepository<Process, UUID> {
}
