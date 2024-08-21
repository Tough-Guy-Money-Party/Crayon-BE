package com.yoyomo.domain.process.domain.repository;

import com.yoyomo.domain.process.domain.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessRepository extends JpaRepository<Process, UUID> {
}
