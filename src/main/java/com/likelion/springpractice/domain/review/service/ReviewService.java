package com.likelion.springpractice.domain.review.service;

import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;

  // 리뷰 생성
  public ReviewResponse createReview(CreateReviewRequest createReviewRequest) {

    Review review = Review.builder()
        .content(createReviewRequest.getContent())
        .score(createReviewRequest.getScore())
        .build();

    Review savedReview = reviewRepository.save(review);

    return reviewMapper.toReviewResponse(savedReview);
  }



}
