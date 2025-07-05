package com.likelion.springpractice.domain.mission.service;

import com.likelion.springpractice.domain.mission.dto.request.IntroductionRequest;
import com.likelion.springpractice.domain.mission.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.mission.dto.response.IntroductionResponse;
import com.likelion.springpractice.domain.mission.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.mission.dto.response.UserSummaryResponse;
import com.likelion.springpractice.domain.mission.entity.Food;
import com.likelion.springpractice.domain.mission.entity.Grade;
import com.likelion.springpractice.domain.mission.entity.Review;
import com.likelion.springpractice.domain.mission.entity.User;
import com.likelion.springpractice.domain.mission.exception.UserErrorCode;
import com.likelion.springpractice.domain.mission.mapper.UserMapper;
import com.likelion.springpractice.domain.mission.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  @Transactional
  public SignUpResponse signUp(SignUpRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new CustomException(UserErrorCode.USERNAME_ALREADY_EXISTS);
    }

    // 비밀번호 인코딩
    String encodePassword = passwordEncoder.encode(request.getPassword());

    // UserMapper를 통해 Entity 생성
    User user = userMapper.toEntity(request, encodePassword);

    // 저장 및 로깅
    User savedUser = userRepository.save(user);
    log.info("New user registered: {}", savedUser.getUsername());

    return userMapper.toSignUpResponse(savedUser);
  }

  //자기소개 등록
  @Transactional
  public IntroductionResponse registerIntroduction(User user, IntroductionRequest request) {

    //유저 존재 확인
    validateUserExist(user);

    if (user.getUsername() == null || user.getPassword() == null) {
      throw new CustomException(UserErrorCode.INTRODUCTION_ALREADY_EXISTS);
    }

    user.createIntroduction(request.getIntroduction());
    User savedUser = userRepository.save(user);

    return userMapper.toIntroductionResponse(savedUser);
  }

  //자기소개 조회
  @Transactional(readOnly = true)
  public IntroductionResponse getIntroduction(User user) {

    validateUserExist(user);

    if (user.getIntroduction() == null || user.getIntroduction().isBlank()) {
      throw new CustomException(UserErrorCode.INTRODUCTION_NOT_FOUND);
    }

    return userMapper.toIntroductionResponse(user);
  }

  //자기소개 수정
  @Transactional
  public IntroductionResponse updateIntroduction(User user, IntroductionRequest request) {
    validateUserExist(user);
    user.createIntroduction(request.getIntroduction());
    User updatedUser = userRepository.save(user);
    return userMapper.toIntroductionResponse(updatedUser);
  }

  @Transactional
  public UserSummaryResponse getUserSummary(User user) {
    if (user == null) {
      throw new CustomException(UserErrorCode.USER_NOT_FOUND);
    }

    // 영속성 컨텍스트 안에서 리뷰 + 배지 모두 fetch
    User loadedUser = userRepository.findByIdWithReviewsAndGrades(user.getId())
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    int reviewCount = loadedUser.getReviewCount();
    // 유저가 작성한 모든 리뷰 → 해당 리뷰의 음식 → 그 음식이 받은 좋아요 수 합산
    int totalLikes = loadedUser.getReviews().stream()
        .map(Review::getFood)                     // 유저가 쓴 리뷰의 음식 가져오기
        .filter(Objects::nonNull)                // 음식이 null이 아닌 경우만
        .mapToInt(Food::getLikeCount) // 각 음식이 받은 좋아요 수
        .sum();                                  // 총합 계산

    List<String> badgeNames = loadedUser.getGrades().stream()
        .map(Grade::getName)
        .toList();

    return UserSummaryResponse.builder()
        .userId(user.getId())
        .username(user.getUsername())
        .role(user.getRole())
        .country(user.getCountry())
        .introduction(user.getIntroduction())
        .reviewCount(reviewCount)
        .likeCount(totalLikes)
        .badges(badgeNames) // ✅ 새로 추가
        .build();
  }


  /**
   * 유저가 리뷰 작성 → User.reviewCount 증가 → UserService.updateBadge()로 배지 자동 반영 → Grade 테이블에 반영됨
   */

  public void updateBadge(User user) {
    // ✅ 영속 상태 보장: fetch join
    User loadedUser = userRepository.findByIdWithGrades(user.getId())
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    int reviewCount = loadedUser.getReviewCount();

    List<String> earnedBadges = new ArrayList<>();
    if (reviewCount >= 5) {
      earnedBadges.add("아기배찌");
    }
    if (reviewCount >= 15) {
      earnedBadges.add("숟가락배찌");
    }
    if (reviewCount >= 30) {
      earnedBadges.add("수저세트배찌");
    }
    if (reviewCount >= 50) {
      earnedBadges.add("우리 서버 아이콘배찌");
    }

    Set<String> currentBadges = loadedUser.getGrades().stream()
        .map(Grade::getName)
        .collect(Collectors.toSet());

    for (String badge : earnedBadges) {
      if (!currentBadges.contains(badge)) {
        Grade newGrade = Grade.builder()
            .name(badge)
            .user(loadedUser)
            .build();
        loadedUser.getGrades().add(newGrade);
      }
    }
  }


  /**
   * 유저 존재 여부 확인
   */
  private void validateUserExist(User user) {
    if (user == null) {
      throw new CustomException(UserErrorCode.USER_NOT_FOUND);
    }
  }

}
