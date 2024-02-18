package com.yoyomo.domain.recruitment.domain.entity;

import com.yoyomo.domain.form.domain.entity.Form;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recruitments")
public class Recruitment {

    @Id
    private String id;

    private String clubId;

    private String title;

    private int generation;

    private String position;

    private Form form;

    private List<Process> process;

    private LocalDateTime deletedAt;
}
