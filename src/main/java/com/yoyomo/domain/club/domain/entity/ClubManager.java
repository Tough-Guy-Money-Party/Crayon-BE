package com.yoyomo.domain.club.domain.entity;

import com.yoyomo.domain.user.domain.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubManager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_manager_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "club_id")
	private Club club;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User manager;

	@Enumerated(EnumType.STRING)
	@Column(name = "manager_role", nullable = false)
	private ManagerRole managerRole;

	private ClubManager(Club club, User user, ManagerRole managerRole) {
		this.club = club;
		this.manager = user;
		this.managerRole = managerRole;
	}

	public static ClubManager asManager(Club club, User manager) {
		return new ClubManager(club, manager, ManagerRole.MANAGER);
	}

	public static ClubManager asOwner(Club club, User manager) {
		return new ClubManager(club, manager, ManagerRole.OWNER);
	}

	public void toManager() {
		this.managerRole = ManagerRole.MANAGER;
	}

	public void toOwner() {
		this.managerRole = ManagerRole.OWNER;
	}

	public boolean isOwner() {
		return managerRole.isOwner();
	}
}
