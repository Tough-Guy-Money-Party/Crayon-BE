package com.yoyomo.domain.user.domain.entity;

import java.time.LocalDateTime;

import com.yoyomo.global.common.entity.BaseEntity;
import com.yoyomo.global.config.jwt.exception.InvalidTokenException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String name;

	private String email;

	@Column(length = 13)
	private String tel;

	private String refreshToken;

	private LocalDateTime deletedAt;

	public User(String name, String email, String tel) {
		this.name = name;
		this.email = email;
		this.tel = tel;
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void checkRefreshToken(String refreshToken) {
		if (!this.refreshToken.equals(refreshToken)) {
			throw new InvalidTokenException();
		}
	}
}
