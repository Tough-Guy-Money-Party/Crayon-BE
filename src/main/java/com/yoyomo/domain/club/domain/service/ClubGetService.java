package com.yoyomo.domain.club.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.repository.ClubRepository;
import com.yoyomo.domain.club.exception.ClubNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubGetService {
    private final ClubRepository clubRepository;

    public Club byId(String id) {
        return clubRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(ClubNotFoundException::new);
    }

    public List<String> extractClubIds(List<Club> clubs) {
        return clubs.stream()
                .map(Club::getId)
                .collect(Collectors.toList());
    }
}
