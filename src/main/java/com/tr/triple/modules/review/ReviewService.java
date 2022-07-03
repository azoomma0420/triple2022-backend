package com.tr.triple.modules.review;


import com.tr.triple.modules.common.DuplicatedIdException;
import com.tr.triple.modules.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ImageService imageService;
    private final ReviewRepository reviewRepository;

    public boolean isNewPlaceReview(Long placeId) {
        List<Review> reviews = reviewRepository.findByPlaceId(placeId);
        if( reviews != null && reviews.size() > 0 )
            return false;
        else
            return true;
    }

    public List<Review> getReviews(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.getById(reviewId);
    }

    public Long addReview(Long userId, String content, Long estimatePoint, Long placeId) throws DuplicatedIdException {
         if(reviewRepository.findByUserIdAndPlaceId(userId, placeId) != null)
             throw new DuplicatedIdException("");
         return reviewRepository.save(Review.builder()
                                                .content(content)
                                                .placeId(placeId)
                                                .userId(userId)
                                                .reviewPoint(estimatePoint).build()).getReviewId();
    }

    public Long modReview(Long reviewId, String content, Long estimatePoint) throws Exception {
        Review review = reviewRepository.getById(reviewId);
        if(review != null) {
            Long oldReviewPoint = review.getReviewPoint();
            review.setContent(content);
            review.setReviewPoint(estimatePoint);
            reviewRepository.save(review);
            return oldReviewPoint;
        }
        throw new Exception("not found reviewId");
    }

    public Long deleteReview(Long reviewId) throws Exception {
        Review review = reviewRepository.getById(reviewId);
        if(review != null) {
            Long oldReviewPoint = review.getReviewPoint();
            reviewRepository.delete(review);

            //delete review images
            imageService.deleteImages(review.getReviewId());

            return oldReviewPoint;
        }
        throw new Exception("not found reviewId");
    }
}
