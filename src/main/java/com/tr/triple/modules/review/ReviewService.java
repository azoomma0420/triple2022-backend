package com.tr.triple.modules.review;


import com.tr.triple.modules.user.UserReview;
import com.tr.triple.modules.user.UserReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserReviewRepository userReviewRepository;

    public List<Review> getReviews(Long userId) {
        return reviewRepository.search(userId);
    }

    public boolean addReview(Long userId, String content) {
        Long reviewId = reviewRepository.save(Review.builder().content(content).build()).getReviewId();
        if(reviewId != null) {
            userReviewRepository.save(UserReview.builder()
                                        .reviewId(reviewId)
                                        .userId(userId).build());
        }
        return false;
    }

    public boolean modReview(Long reviewId, String content) {
        Review review = reviewRepository.getById(reviewId);
        if(review != null) {
            review.setContent(content);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    public boolean deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.getById(reviewId);
        if(review != null) {
            reviewRepository.delete(review);
        }

        UserReview userReview = userReviewRepository.findByUserIdAndReviewId(userId, reviewId);
        if(userReview != null) {
            userReviewRepository.delete(userReview);
        }
        return true;
    }
}
