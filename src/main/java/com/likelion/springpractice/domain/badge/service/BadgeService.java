package com.likelion.springpractice.domain.badge.service;


import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.dto.response.OwnedBadgeResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.exception.BadgeErrorCode;
import com.likelion.springpractice.domain.badge.mapper.BadgeMapper;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.own.entity.Own;
import com.likelion.springpractice.domain.own.repository.OwnRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeService {

  private final BadgeRepository badgeRepository;
  private final BadgeMapper badgeMapper;
  private final UserRepository userRepository;
  private final OwnRepository ownRepository;


  // 존재하는 배지 전체 조회
  @Transactional
  public List<BadgeResponse> getAllBadges() {
    List<Badge> badgeList = badgeRepository.findAll();

    return badgeList.stream().map(badgeMapper::toBadgeResponse).toList();
  }


  // 배지 id로, 단일 배지 상세 보기
  @Transactional
  public BadgeResponse getBadgeById(Long badgeId) {
    // 넘겨받은 아이디와 같은 배지 찾기. 없으면 에러
    Badge badge = badgeRepository.findById(badgeId).orElseThrow(() -> new CustomException(
        BadgeErrorCode.BADGE_NOT_FOUND));

    // 해당 배지의 정보를 리턴.
    return badgeMapper.toBadgeResponse(badge);
  }

  // 존재하는 배지 전체 조회(획득 여부 포함)
  @Transactional
  public List<OwnedBadgeResponse> getAllOwnedBadges(String userName) {
    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByUsername(userName)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 2. 존재하는 배지 전체를 가져옴.
    List<Badge> badgeList = badgeRepository.findAll();

    // 3. badgeList의 요소와 user가 true로 저장되어있으면 State=true, false거나 저장이 안되어 있으면 State=false로 해서, 전체 배지의 획득 여부를 저장.
    return badgeList.stream()
        .map(badge -> {
          Optional<Own> own = ownRepository.findByUserAndBadge(user, badge);
          boolean state = own.map(Own::isState).orElse(false);
          return badgeMapper.toOwnedBadgeResponse(badge, state);
        })
        .toList();
  }


}
