package com.yoyomo.domain.recruitment.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recruitments")
public class Recruitment {
    @Id
    private String id;
    private String formId;
    private String name;
    private int generation;
    private Map<String, Schedule> calendar;
}
