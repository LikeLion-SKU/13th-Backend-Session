package com.likelion.springpractice.domain.badge.service;

import com.likelion.springpractice.domain.badge.dto.request.CreateBadgeRequest;
import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    //배찌 생성
    @Transactional
    public BadgeResponse createBadge(CreateBadgeRequest createBadgeRequest) {
        //유효성 검사
        Badge badge = Badge.builder()
            .name(createBadgeRequest.getName())
            .acquisition_condition(createBadgeRequest.getAcquisition_condition())
            .build();
        badgeRepository.save(badge);
        //로그 처리

        return toBadgeResponse(badge);
    }

    //음식 전체 조회
    public List<BadgeResponse> getAllBadges() {
        List<Badge> badgeList = badgeRepository.findAll();
        return badgeList.stream().map(this::toBadgeResponse).toList();
    }

    //음식 단일 조회
    public BadgeResponse getBadgeById(Long id) {
        Badge badge = badgeRepository.findById(id)
            .orElseThrow();
        return toBadgeResponse(badge);
    }

    //음식 삭제
    @Transactional
    public Boolean deleteBadge(Long id) {
        Badge badge = badgeRepository.findById(id)
            .orElseThrow();
        badgeRepository.delete(badge);
        return true;
    }

    private BadgeResponse toBadgeResponse(Badge badge) {
        return BadgeResponse.builder().badgeId(badge.getId())
            .name(badge.getName()).acquisition_condition(badge.getAcquisition_condition()).build();
    }
}
