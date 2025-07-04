package com.likelion.springpractice.week11.service;

import com.likelion.springpractice.week11.domain.Food;
import com.likelion.springpractice.week11.domain.Review;
import com.likelion.springpractice.week11.dto.response.FoodDetailResponseDto;
import com.likelion.springpractice.week11.dto.response.ReviewSimpleDto;
import com.likelion.springpractice.week11.repository.FoodLikeRepository;
import com.likelion.springpractice.week11.repository.FoodRepository;
import com.likelion.springpractice.week11.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodLikeRepository foodLikeRepository;
    private final ReviewRepository reviewRepository;

    // 음식 상세 조회
    public FoodDetailResponseDto getFoodDetail(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("음식을 찾을 수 없습니다."));

        // 평점 계산
        List<Review> reviews = reviewRepository.findAllByFoodId(foodId);
        double averageRating = reviews.isEmpty() ? 0.0 :
                reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);        // 좋아요 수
        int likeCount = foodLikeRepository.findAll().stream()
                .filter(like -> like.getFood().getId().equals(foodId) && like.getDeletedAt() == null)
                .toList().size();

        // 후기 리스트 DTO로 변환
        List<ReviewSimpleDto> reviewDtos = reviews.stream()
                .filter(r -> r.getDeletedAt() == null)
                .map(review -> new ReviewSimpleDto(
                        review.getId(),
                        review.getUser().getNickname(),
                        review.getRating(),
                        review.getContent(),
                        review.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return FoodDetailResponseDto.builder()
                .id(food.getId())
                .name(food.getName())
                .description(food.getDescription())
                .averageRating(averageRating)
                .likeCount(likeCount)
                .reviews(reviewDtos)
                .build();
    }

    // 인기 음식 조회 (좋아요 수 기준)
    public List<FoodDetailResponseDto> getPopularFoods() {
        return foodRepository.findAll().stream()
                .map(food -> FoodDetailResponseDto.builder()
                        .id(food.getId())
                        .name(food.getName())
                        .description(food.getDescription())
                        .averageRating(calculateAvgRating(food.getId()))
                        .likeCount(countLikes(food.getId()))
                        .reviews(null)
                        .build())

                .sorted(Comparator.comparingInt(FoodDetailResponseDto::getLikeCount).reversed())
                .collect(Collectors.toList());
    }

    private double calculateAvgRating(Long foodId) {
        List<Review> reviews = reviewRepository.findAllByFoodId(foodId);
        return reviews.isEmpty() ? 0.0 :
                reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);    }

    private int countLikes(Long foodId) {
        return (int) foodLikeRepository.findAll().stream()
                .filter(like -> like.getFood().getId().equals(foodId) && like.getDeletedAt() == null)
                .count();
    }
}