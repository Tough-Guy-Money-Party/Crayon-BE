package com.yoyomo.domain.user.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class User {

    private String name;

    private String email;

    private String tel;
}
