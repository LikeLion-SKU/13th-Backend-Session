package com.likelion.springpractice.domain.badge.evaluator;

import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.foodreview.repository.FoodReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.userbadge.service.UserBadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewBadgeEvaluator implements BaseBadgeEvaluator {

    private final FoodReviewRepository foodReviewRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeService userBadgeService;


    @Override
    public void evaluate(User user) {
        Long count = foodReviewRepository.countByUserId(user.getId());
        System.out.println(count);

        if (count == 5) {
            Badge badge = badgeRepository.findByName("아기배찌");
            userBadgeService.assignBadgeIfNotExists(user, badge);
        } else if (count == 15) {
            Badge badge = badgeRepository.findByName("숟가락배찌");
            userBadgeService.assignBadgeIfNotExists(user, badge);
        } else if (count == 30) {
            Badge badge = badgeRepository.findByName("수저세트배찌");
            userBadgeService.assignBadgeIfNotExists(user, badge);
        }
        
    }
}
