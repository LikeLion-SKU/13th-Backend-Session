package com.likelion.springpractice.domain.badge.evaluator;

import com.likelion.springpractice.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BadgeConditionEvaluator implements BaseBadgeEvaluator {

    private final ReviewBadgeEvaluator reviewBadgeEvaluator;

    @Override
    public void evaluate(User user) {
        reviewBadgeEvaluator.evaluate(user);
    }
}
