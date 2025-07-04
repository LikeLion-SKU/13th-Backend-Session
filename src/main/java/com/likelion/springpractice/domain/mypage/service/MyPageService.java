package com.likelion.springpractice.domain.mypage.service;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.mapper.BadgeMapper;
import com.likelion.springpractice.domain.badge.repository.UserBadgeRepository;
import com.likelion.springpractice.domain.like.dto.response.LikedFoodResponse;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.dto.response.UserResponse;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.mapper.UserMapper;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final ReviewRepository reviewRepository;
  private final UserBadgeRepository userBadgeRepository;

  /**
   * 사용자 정보를 조회하는 서비스 메서드.
   * <p>
   * 주어진 사용자 ID로 데이터베이스에서 {@link User} 엔티티를 조회하고, 이를 {@link UserResponse} DTO로 변환하여 반환한다.
   * </p>
   *
   * @param userId 조회할 사용자의 ID
   * @return 조회된 사용자 정보가 담긴 {@link UserResponse} 객체
   * @throws CustomException {@link UserErrorCode#USER_NOT_FOUND} – 해당 ID의 사용자가 존재하지 않는 경우 발생
   */
  public UserResponse getUserInfo(Long userId) {
    log.info("[서비스] 사용자 정보 조회: userId = {}", userId);
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    return UserMapper.toUserResponse(user);
  }

  /**
   * 사용자가 좋아요한 음식 목록을 조회하는 서비스 메서드.
   * <p>
   * 주어진 {@link User} 객체로부터 해당 사용자가 좋아요한 음식들의 목록을 조회하고, 이를 {@link LikedFoodResponse} DTO 리스트로 변환하여
   * 반환한다.
   * </p>
   *
   * @param user 조회할 사용자 정보가 담긴 {@link User} 객체
   * @return 사용자가 좋아요한 음식들의 목록이 담긴 {@link List<LikedFoodResponse>} 객체
   */
  @Transactional(readOnly = true)
  public List<LikedFoodResponse> getLikedFoodsByUser(User user) {
    log.info("[서비스] 사용자가 좋아요한 음식 목록 조회 시도: userName={}", user.getUsername());
    List<Like> likes = likeRepository.findAllByUser(user);

    return likes.stream()
        .map(like -> new LikedFoodResponse(like.getFood().getId(), like.getFood().getName()))
        .collect(Collectors.toList());
  }

  /**
   * 사용자가 작성한 리뷰 목록을 조회하는 서비스 메서드.
   * <p>
   * 주어진 {@link User} 객체로부터 해당 사용자가 작성한 리뷰들의 목록을 조회하고, 이를 {@link LikedFoodResponse} DTO 리스트로 변환하여
   * 반환한다.
   * </p>
   *
   * @param user 조회할 사용자 정보가 담긴 {@link User} 객체
   * @return 사용자가 작성한 리뷰들의 목록이 담긴 {@link List<LikedFoodResponse>} 객체
   */
  @Transactional(readOnly = true)
  public List<LikedFoodResponse> getReviewsByUser(User user) {
    log.info("[서비스] 사용자가 작성한 리뷰 목록 조회 시도: userName={}", user.getUsername());
    return reviewRepository.findByUser(user).stream()
        .map(review -> new LikedFoodResponse(review.getFood().getId(), review.getFood().getName()))
        .collect(Collectors.toList());
  }

  /**
   * 사용자가 보유한 뱃지를 조회하는 서비스 메서드.
   * <p>
   * 주어진 {@link User} 객체로부터 해당 사용자가 보유한 뱃지들의 목록을 조회하고, 이를 {@link BadgeResponse} DTO 리스트로 변환하여 반환한다.
   * </p>
   *
   * @param user 조회할 사용자 정보가 담긴 {@link User} 객체
   * @return 사용자가 보유한 뱃지들의 목록이 담긴 {@link List<BadgeResponse>} 객체
   */
  public List<BadgeResponse> getBadgeByUser(User user) {
    log.info("[서비스] 유저의 뱃지 조회 시도: userId = {}", user.getUserId());
    return userBadgeRepository.findAllByUser(user).stream()
        .map(userBadge -> BadgeMapper.toResponse(userBadge.getBadge()))
        .toList();
  }

}




