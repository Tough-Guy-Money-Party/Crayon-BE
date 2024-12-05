package com.yoyomo.domain.club.domain.entity;

import lombok.Getter;

@Getter
public enum ManagerRole {
    OWNER,
    MANAGER;

    public boolean isOwner() {
        return this == OWNER;
    }
}
