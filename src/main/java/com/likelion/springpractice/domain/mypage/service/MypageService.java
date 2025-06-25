package com.likelion.springpractice.domain.mypage.service;

import com.likelion.springpractice.domain.MappingUserBadge.entity.MappingUserBadge;
import com.likelion.springpractice.domain.MappingUserBadge.repository.MappingUserBadgeRepository;
import com.likelion.springpractice.domain.badge.entity.BadgeName;
import com.likelion.springpractice.domain.mypage.dto.response.MypageBadgesResponse;
import com.likelion.springpractice.domain.mypage.dto.response.MypageLikesResponse;
import com.likelion.springpractice.domain.mypage.dto.response.MypageReviewsResponse;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MypageService {

  private final UserRepository userRepository;
  private final MappingUserBadgeRepository mappingUserBadgeRepository;

  // 특정 사용자의 좋아요 리스트 반환
  @Transactional(readOnly = true)
  public List<MypageLikesResponse> getLikeFoods(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
   return user.getLikes().stream()
       .filter(like -> like.isStatus()) // status가 true인 경우
       .map(like -> MypageLikesResponse.builder()
           .foodId(like.getFood().getFoodId())
           .foodName(like.getFood().getFoodName())
           .description(like.getFood().getDescription())
           .build())
       .toList();
  }

  // 특정 사용자의 리뷰 반환
  @Transactional(readOnly = true)
  public List<MypageReviewsResponse> getReviewFoods(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    return user.getReviews().stream()
        .map(review -> MypageReviewsResponse.builder()
            .foodId(review.getFood().getFoodId())
            .foodName(review.getFood().getFoodName())
            .content(review.getContent())
            .build())
        .toList();
  }

  // 특정 사용자의 보유 배찌와 각 배찌 개수 반환
  @Transactional(readOnly = true)
  public List<MypageBadgesResponse> getUserBadges(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 특정 user의 배찌를 MappingUserBadge 타입의 List로 저장
    List<MappingUserBadge> userBadges = mappingUserBadgeRepository.findAllByUser(user);

    // Map<BadgeName, Long>으로 그룹핑 + 카운팅
    Map<BadgeName, Long> badgeCountMap = userBadges.stream()
        .collect(Collectors.groupingBy(
            mapping -> mapping.getBadge().getBadgeName(),
            Collectors.counting()
        ));

    return badgeCountMap.entrySet().stream()
        .map(entry -> MypageBadgesResponse.builder()
            .badgeName(entry.getKey())
            .badgeNum(entry.getValue().intValue())
            .build())
        .toList();
  }

}
