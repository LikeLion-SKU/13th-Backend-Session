package com.likelion.springpractice.week11.service;

import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.week11.domain.Food;
import com.likelion.springpractice.week11.domain.Review;
import com.likelion.springpractice.week11.dto.request.CreateReviewRequestDto;
import com.likelion.springpractice.week11.dto.response.ReviewResponseDto;
import com.likelion.springpractice.week11.repository.FoodRepository;
import com.likelion.springpractice.week11.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    // 후기 작성
    public void createReview(Long userId, CreateReviewRequestDto requestDto) {
        Food food = foodRepository.findById(requestDto.getFoodId())
                .orElseThrow(() -> new IllegalArgumentException("해당 음식이 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Review review = Review.builder()
                .food(food)
                .user(user)
                .content(requestDto.getContent())
                .rating(requestDto.getRating())
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
    }

    // 특정 음식의 후기 조회
    public List<ReviewResponseDto> getReviewsByFood(Long foodId) {
        return reviewRepository.findAllByFoodId(foodId).stream()
                .filter(review -> review.getDeletedAt() == null)
                .map(ReviewResponseDto::from)
                .collect(Collectors.toList());
    }

    // 후기 soft delete
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("후기를 찾을 수 없습니다."));

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 후기만 삭제할 수 있습니다.");
        }

        review.setDeletedAt(LocalDateTime.now());
        reviewRepository.save(review);
    }

    // 마이페이지용
    public List<ReviewResponseDto> getReviewsByUser(Long userId) {
        List<Review> reviews = reviewRepository.findAllByUserId(userId);
        return reviews.stream()
                .map(ReviewResponseDto::from)
                .toList();
    }
}