package com.likelion.springpractice.domain.review.service;


import com.likelion.springpractice.domain.badge.entity.Badge;
import com.likelion.springpractice.domain.badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.mapper.FoodMapper;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.own.entity.Own;
import com.likelion.springpractice.domain.own.repository.OwnRepository;
import com.likelion.springpractice.domain.post.exception.PostErrorCode;
import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.review.dto.request.ReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.exception.ReviewErrorCode;
import com.likelion.springpractice.domain.review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateResponse;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.global.exception.GlobalErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;
  private final UserRepository userRepository;
  private final FoodRepository foodRepository;
  private final BadgeRepository badgeRepository;
  private final OwnRepository ownRepository;




  // (확인용)후기 Id로, 후기 단일 조회
  @Transactional
  public ReviewResponse getReview(Long reviewId) {
    // 넘겨받은 reviewId와 같은 id 찾기. 없으면 에러
    Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(
        ReviewErrorCode.REVIEW_NOT_FOUND));

    // 해당 음식의 정보를 리턴.
    return reviewMapper.toReviewResponse(review);
  }


  // (확인용)후기 리스트 전체 조회
  @Transactional
  public List<ReviewResponse> getAllReviews() {
    List<Review> reviewList = reviewRepository.findAll();

    return reviewList.stream().map(reviewMapper::toReviewResponse).toList();
  }



  // 음식별 후기 리스트 전체 조회
  @Transactional
  public List<ReviewResponse> getAllReviewsByFoodId(Long foodId) {

    Food food = foodRepository.findById(foodId).orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));
    List<Review> reviewList = reviewRepository.findByFood(food);

    return reviewList.stream().map(reviewMapper::toReviewResponse).toList();
  }


  // 회원별 후기 리스트 전체 조회
  @Transactional
  public List<ReviewResponse> getAllReviewsByUserName(String userName) {

    User user = userRepository.findByUsername(userName).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    List<Review> reviewList = reviewRepository.findByUser(user);


    return reviewList.stream().map(reviewMapper::toReviewResponse).toList();
  }




  // 후기 작성
  @Transactional
  public ReviewResponse createReview(String userName, ReviewRequest request) {

    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByUsername(userName)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 2. 음식 id로 해당 음식 있는지 확인
    Food food = foodRepository.findById(request.getFoodId())
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    // 3. 전에 썼던 리뷰를 찾아서, 존재하면 에러
    if(reviewRepository.existsByUserAndFood(user, food)){
      throw new CustomException(ReviewErrorCode.REVIEW_ALREADY_EXISTS);
    }

    // 4. review 객체를 만듦.
    Review review = Review.builder()
        .rate(request.getRate())
        .contents(request.getContent())
        .user(user)
        .food(food)
        .build();

    // 5. Review 엔티티로 레포지토리에 접근(저장)
    Review savedReview = reviewRepository.save(review);
    log.info("New review created: {}", savedReview.getId());

    // 6. 음식의 평점을 조정해줌. (해당 음식의 평점 총합 / 해당 음식 후기 개수)
    Long reviewCount = reviewRepository.countByFoodId(food.getId());
    Double newRate;

    if (reviewCount == 0) {
      // 후기 없으면 baseRate 사용
      newRate = food.getBaseRate() != null ? food.getBaseRate() : 0.0;
    } else {
      Double reviewSum = reviewRepository.sumRateByFoodId(food.getId());
      if (reviewSum == null) reviewSum = 0.0;
      newRate = Math.round((reviewSum / reviewCount) * 10) / 10.0;
    }

    food.setRate(newRate);
    foodRepository.save(food);

    // 7. 후기 개수가 조건에 맞다면 배지 획득
    // 사용자가 쓴 후기 개수 가져오기
    Long reviewNum = reviewRepository.countByUser(user);

    // 전체 배지 리스트 가져오기
    List<Badge> badgeList = badgeRepository.findAll();


    // 배지.count <= reviewNum 라면 Own.state를 true로.
    for(int i = 0; i<badgeList.size(); i++){
      Badge badge = badgeList.get(i);
      if(badge.getCount() <= reviewNum){
        Optional<Own> own = ownRepository.findByUserAndBadge(user, badge);

        if(own.isPresent()){
          // 현황에 있으면 true로.
          Own updateOwn = own.get();
          updateOwn.setState(true);
          ownRepository.save(updateOwn);
        }
        else{
          // 현황에 없으면 추가.
          Own newOwn = Own.builder()
              .state(true)
              .user(user)
              .badge(badge)
              .build();
          ownRepository.save(newOwn);
        }
      }
      else{
        break;
      }
    }

    return reviewMapper.toReviewResponse(savedReview);
  }


  // 후기 수정
  @Transactional
  public ReviewResponse updateReview(Long reviewId, String userName, ReviewRequest request) {
    // 1. 리뷰 찾기
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND));

    // 2. 로그인한 사용자와 리뷰 작성자가 일치하는지 확인
    if (!review.getUser().getUsername().equals(userName)) {
      throw new CustomException(ReviewErrorCode.NO_PERMISSION_TO_UPDATE);
    }

    // 3. 음식 정보 가져오기
    Food food = review.getFood();

    // 요청으로 들어온 음식 ID와 실제 리뷰가 가리키는 음식 ID가 다르면 에러
    if (!food.getId().equals(request.getFoodId())) {
      throw new CustomException(ReviewErrorCode.INVALID_FOOD_ID_FOR_REVIEW_UPDATE);
    }

    // 4. 후기 평점, 후기 내용만 수정
    review.setRate(request.getRate());
    review.setContents(request.getContent());

    // 5. 음식의 평점을 조정해줌. (해당 음식의 평점 총합 / 해당 음식 후기 개수)
    Long reviewCount = reviewRepository.countByFoodId(food.getId());
    Double newRate;

    if (reviewCount == 0) {
      // 후기 없으면 baseRate 사용
      newRate = food.getBaseRate() != null ? food.getBaseRate() : 0.0;
    } else {
      Double reviewSum = reviewRepository.sumRateByFoodId(food.getId());
      if (reviewSum == null) reviewSum = 0.0;
      newRate = Math.round((reviewSum / reviewCount) * 10) / 10.0;
    }

    food.setRate(newRate);
    foodRepository.save(food);

    // 6. Review 엔티티로 레포지토리에 접근(수정)
    return reviewMapper.toReviewResponse(review);

  }


  // 후기 삭제
  @Transactional
  public boolean deleteReview(String userName, Long id) {
    log.info("[서비스] 후기 삭제 시도: id= {}", id);
    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByUsername(userName)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));


    // 2. 후기 id로 해당 후기가 존재하는지 체크
    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 후기 삭제 실패 - 존재하지 않음: id= {}", id);
          return new CustomException(PostErrorCode.POST_ERROR_FOUND);
        });

    // 3. 해당 후기를 삭제
    reviewRepository.deleteById(id);
    log.info("[서비스] 후기 삭제 완료: id= {}", id);


    // 4. 평점 다시 계산
    Food food = review.getFood();

    Long reviewCount = reviewRepository.countByFoodId(food.getId());
    Double newRate;

    if (reviewCount == 0) {
      // 후기 없으면 baseRate로
      newRate = food.getBaseRate() != null ? food.getBaseRate() : 0.0;
    } else {
      Double reviewSum = reviewRepository.sumRateByFoodId(food.getId());
      newRate = Math.round((reviewSum / reviewCount) * 10) / 10.0;
    }

    food.setRate(newRate);
    foodRepository.save(food);
    
    
    // 5. 배지 획득 현황 다시 검사해서 반영
    // 사용자가 쓴 후기 개수 가져오기
    Long reviewNum = reviewRepository.countByUser(user);

    // 전체 배지 리스트 가져오기
    List<Badge> badgeList = badgeRepository.findAll();


    // 배지.count > reviewNum 라면 Own.state를 false로
    for(int i = badgeList.size() -1 ; i >= 0; i--){
      Badge badge = badgeList.get(i);
      if(badge.getCount() > reviewNum){
        Optional<Own> own = ownRepository.findByUserAndBadge(user, badge);

        if(own.isPresent()){
          // 현황에 있으면 true로.
          Own updateOwn = own.get();
          updateOwn.setState(false);
          ownRepository.save(updateOwn);
        }
      }
      else{
        break;
      }
    }

    return true;
  }



}
