package com.yoyomo.domain.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "applicants")
public class Applicant extends User {
    private String phone;
    private String email;
//    @DBRef
//    private List<Club> clubs = new ArrayList<>();
}
