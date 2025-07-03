package com.likelion.springpractice.domain.own.service;


import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.mapper.BadgeMapper;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.like.mapper.LikeMapper;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.own.dto.response.OwnResponse;
import com.likelion.springpractice.domain.own.entity.Own;
import com.likelion.springpractice.domain.own.mapper.OwnMapper;
import com.likelion.springpractice.domain.own.repository.OwnRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OwnService {

  private final OwnRepository ownRepository;
  private final OwnMapper ownMapper;
  private final BadgeRepository badgeRepository;
  private final BadgeMapper badgeMapper;
  private final UserRepository userRepository;


  // 내가 획득한 배지 목록 조회
  @Transactional
  public List<BadgeResponse> getBadgeOwnList(String userName) {
    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByUsername(userName)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 2. 해당 아이디로 획득한 배지 현황 목록 리스트 가져오기
    List<Own> ownList = ownRepository.findAllByUserAndStateIsTrue(user);

    // 3. 배지 현황 목록에 저장된 배지 id 보고, 배지 리스트 가져오기
    List<Badge> badgeList = ownList.stream()
        .map(Own::getBadge)
        .collect(Collectors.toList());

    // 4. 배지 리스트를 리턴
    return badgeList.stream()
        .map(badgeMapper::toBadgeResponse)
        .collect(Collectors.toList());
  }
 

  // 내 배지 현황 목록 조회
  @Transactional
  public List<OwnResponse> getBadgeStateList(String userName) {
    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByUsername(userName)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 2. 해당 아이디로 저장된 배지 현황 목록 리스트 가져오기
    List<Own> ownList = ownRepository.findAllByUser(user);

    // 3. 해당 아이디의 배지 획득 현황 리스트를 리턴
    return ownList.stream().map(ownMapper::toOwnResponse).toList();
  }



}
