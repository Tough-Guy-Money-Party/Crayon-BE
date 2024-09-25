package com.yoyomo.domain.club.application.usecase;

import com.yoyomo.domain.club.application.dto.request.ClubRequestDTO;
import com.yoyomo.domain.club.application.dto.response.ClubResponseDTO;
import com.yoyomo.domain.club.application.mapper.ClubMapper;
import com.yoyomo.domain.club.domain.entity.Club;
import com.yoyomo.domain.club.domain.entity.ClubManager;
import com.yoyomo.domain.club.domain.service.*;
import com.yoyomo.domain.club.exception.DuplicatedSubDomainException;
import com.yoyomo.domain.user.application.dto.response.ManagerResponseDTO;
import com.yoyomo.domain.user.application.mapper.ManagerMapper;
import com.yoyomo.domain.user.domain.entity.Manager;
import com.yoyomo.domain.user.domain.service.UserGetService;
import com.yoyomo.infra.aws.s3.service.S3Service;
import com.yoyomo.infra.aws.service.AwsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.*;
import static com.yoyomo.domain.club.application.dto.request.ClubRequestDTO.Save;
import static com.yoyomo.domain.club.application.dto.response.ClubResponseDTO.*;
import static com.yoyomo.domain.club.domain.entity.Club.checkAuthority;
import static com.yoyomo.domain.club.domain.entity.Club.checkDuplicateParticipate;

@Service
@RequiredArgsConstructor
public class ClubManageUseCaseImpl implements ClubManageUseCase {

    private final UserGetService userGetService;
    private final ClubSaveService clubSaveService;
    private final ClubManagerSaveService clubManagerSaveService;
    private final ClubMapper clubMapper;
    private final ClubGetService clubGetService;
    private final ClubUpdateService clubUpdateService;
    private final ManagerMapper managerMapper;
    private final ClubManagerGetService clubManagerGetService;
    private final ClubManagerDeleteService clubManagerDeleteService;
    private final S3Service s3Service;
    private final AwsService awsService;

    private final String BASEURL = ".crayon.land";

    //TODO: 배포시 기본 next 정적파일을 업로드하는 로직 추가
    @Override @Transactional
    public Response save(Save dto, Long userId) throws IOException{
        String subDomain = checkDuplicatedSubDomain(dto.subDomain()) + BASEURL;

        //버킷 생성
        s3Service.createBucket(subDomain);

        //배포 생성
        awsService.distribute(subDomain);

        //정적파일 업로드
        s3Service.save(subDomain);
        Manager manager = userGetService.find(userId);
        Club club = clubSaveService.save(dto);
        mapFK(manager, club);


        return clubMapper.toResponse(club);
    }

    @Override
    public Response read(String clubId) {
        Club club = clubGetService.find(clubId);
        return clubMapper.toResponse(club);
    }

    @Override
    public void update(String clubId, Save dto, Long userId) {
        checkDuplicatedSubDomain(dto.subDomain());
        Club club = checkAuthorityByClub(clubId, userId);
        clubUpdateService.update(club, dto);
    }

    @Override
    public void delete(String clubId, Long userId) {
        Club club = checkAuthorityByClub(clubId, userId);
        clubUpdateService.delete(club);
    }

    @Override
    public List<ManagerResponseDTO.ManagerInfo> getManagers(String clubId, Long userId) {
        Club club = checkAuthorityByClub(clubId, userId);

        return club.getClubManagers().stream()
                .map(ClubManager::getManager)
                .map(managerMapper::toManagerInfoDTO)
                .toList();
    }

    @Override @Transactional
    public ClubResponseDTO.Participation participate(ClubRequestDTO.Participation dto, Long userId) {
        Club club = clubGetService.findByCode(dto.code());
        Manager manager = userGetService.find(userId);

        checkDuplicateParticipate(club, manager);
        mapFK(manager, club);

        return clubMapper.toParticipation(club);
    }

    @Override
    public Code readCode(String clubId, Long userId) {
        Club club = clubGetService.find(clubId);
        Manager manager = userGetService.find(userId);
        checkAuthority(club, manager);
        return new Code(club.getCode());
    }

    @Override
    public Code updateCode(String clubId, Long userId) {
        Club club = clubGetService.find(clubId);
        Manager manager = userGetService.find(userId);
        checkAuthority(club, manager);
        return new Code(clubUpdateService.update(club));
    }

    @Override
    public void deleteManagers(Delete dto, Long userId) {
        Club club = checkAuthorityByClub(dto.clubId(), userId);
        for (Long id : dto.userIds()) {
            Manager manager = userGetService.find(id);
            ClubManager clubManager = clubManagerGetService.find(club, manager);
            clubManagerDeleteService.delete(clubManager);
        }
    }

    @Override
    public List<Response> readAll(Long userId) {
        Manager manager = userGetService.find(userId);

        return manager.getClubManagers().stream()
                .map(ClubManager::getClub)
                .map(clubMapper::toResponse)
                .toList();
    }

    private String checkDuplicatedSubDomain(String subDomain) {
        if(clubGetService.checkSubDomain(subDomain))
            throw new DuplicatedSubDomainException();
        return subDomain;
    }

    private void mapFK(Manager manager, Club club) {
        ClubManager clubManager = clubManagerSaveService.save(manager, club);
        manager.addClubManager(clubManager);
        club.addClubManager(clubManager);
    }

    private Club checkAuthorityByClub(String clubId, Long userId) {
        Club club = clubGetService.find(clubId);
        Manager manager = userGetService.find(userId);
        checkAuthority(club, manager);

        return club;
    }
}
