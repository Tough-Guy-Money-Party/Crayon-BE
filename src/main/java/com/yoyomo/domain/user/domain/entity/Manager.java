package com.yoyomo.domain.user.domain.entity;

import com.yoyomo.domain.club.domain.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "managers")
public class Manager extends User{
    private String email;
    @DBRef
    private List<Club> clubs = new ArrayList<>();
}
