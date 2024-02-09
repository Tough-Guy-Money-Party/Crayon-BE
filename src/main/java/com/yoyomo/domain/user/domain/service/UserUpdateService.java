package com.yoyomo.domain.user.domain.service;

import com.mongodb.client.result.UpdateResult;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.user.application.dto.req.UserUpdateRequest;
import com.yoyomo.domain.user.application.dto.res.UserResponse;
import com.yoyomo.domain.user.domain.entity.User;
import com.yoyomo.domain.user.domain.repository.UserRepository;
import com.yoyomo.global.config.jwt.JwtProvider;
import com.yoyomo.global.config.jwt.presentation.JwtResponse;
import com.yoyomo.global.config.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUpdateService {
    private final UserRepository userRepository;

    public Void updateByEmail(String email) {
        return null;
    }

    public Void deleteByEmail(String email) {
        return null;
    }

    public void addClub(String userEmail, Club club) {
        User user = userRepository.findByEmail(userEmail).get();
        List<Club> clubs = user.getClubs();
        clubs.add(club);
        userRepository.save(user);
    }
}
