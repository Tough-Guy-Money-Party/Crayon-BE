package com.yoyomo.domain.application.domain.entity;


import com.yoyomo.domain.application.domain.entity.enums.Rating;
import com.yoyomo.domain.application.domain.entity.enums.Status;
import com.yoyomo.domain.process.domain.entity.Process;
import com.yoyomo.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "application_id")
    private UUID id;

    @Embedded
    private User user;

    @ManyToOne
    private Process process;

    private Status status;

    private Rating averageRating;

    private String answerId;
}
