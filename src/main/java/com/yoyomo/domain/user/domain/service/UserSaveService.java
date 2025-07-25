package com.yoyomo.domain.user.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSaveService {
	private final UserRepository userRepository;

	public User save(User manager) {
		return userRepository.save(manager);
	}
}
