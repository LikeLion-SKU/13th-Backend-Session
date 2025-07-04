package com.likelion.springpractice.domain.userbadge.service;

import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.domain.userbadge.dto.response.UserBadgeResponse;
import com.likelion.springpractice.domain.userbadge.entity.UserBadge;
import com.likelion.springpractice.domain.userbadge.repository.UserBadgeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserBadgeService {

    private final UserBadgeRepository userBadgeRepository;
    private final UserRepository userRepository;

    public List<UserBadgeResponse> getAllUserBadges(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow();

        List<UserBadge> badgeList = userBadgeRepository.findAllByUserId(userId);

        return badgeList.stream().map(this::toUserBadgeResponse).toList();
    }

    @Transactional
    public void assignBadgeIfNotExists(User user, Badge badge) {
        if (!userBadgeRepository.existsByUserIdAndBadgeId(user.getId(), badge.getId())) {
            UserBadge userBadge = UserBadge.builder()
                .user(user)
                .badge(badge)
                .build();

            userBadgeRepository.save(userBadge);
        }
    }

    private UserBadgeResponse toUserBadgeResponse(UserBadge userBadge) {
        return UserBadgeResponse.builder().badgeId(userBadge.getBadge().getId())
            .badgeName(userBadge.getBadge().getName()).build();
    }


}
