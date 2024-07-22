package com.yoyomo.domain.club.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoyomo.domain.user.domain.entity.Manager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clubs")
public class Club {
    @Id
    private String id;
    private String name;
    private String subDomain;
    private String notionPageLink;
    private String favicon;
    private String image;
    private String siteTitle;
    private LocalDateTime deletedAt;
    
    @DBRef
    @JsonIgnore
    private List<Manager> managers = new ArrayList<>();
}
