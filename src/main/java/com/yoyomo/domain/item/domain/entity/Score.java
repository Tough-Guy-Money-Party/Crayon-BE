package com.yoyomo.domain.item.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Score extends Item {

	private String meaningOfHigh;

	private String meaningOfLow;

	private int score;
}
