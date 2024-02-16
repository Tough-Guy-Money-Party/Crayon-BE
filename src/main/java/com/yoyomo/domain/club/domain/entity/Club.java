package com.yoyomo.domain.club.domain.entity;

import com.yoyomo.domain.user.domain.entity.User;
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
    private LocalDateTime deletedAt;
    @DBRef
    private List<User> managers = new ArrayList<>();
}
