package com.yoyomo.domain.application.domain.model;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Replies {

    private final List<Reply> replies;

    public int size() {
        return replies.size();
    }

    public Reply get(int index) {
        return replies.get(index);
    }
}
