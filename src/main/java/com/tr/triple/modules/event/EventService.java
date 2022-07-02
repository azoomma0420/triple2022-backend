package com.tr.triple.modules.event;


import com.tr.triple.modules.code.ActionType;
import com.tr.triple.modules.code.EventType;
import com.tr.triple.modules.code.ServiceType;
import com.tr.triple.modules.image.ImageService;
import com.tr.triple.modules.review.ReviewService;
import com.tr.triple.modules.user.TripleUser;
import com.tr.triple.modules.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final UserService userService;
    private final ImageService imageService;
    private final ReviewService reviewService;

    public void handleEvent(TripleUser user, EventDTO event) throws Exception {
        validation(event);
        if(event.getType().equals(EventType.REVIEW.getCode())) {
            handleReviewEvent(user, event);
        }
    }

    private void validation(EventDTO event) throws Exception {
        if(ServiceType.valueOf(event.getType()) == null)
            throw new EventParameterValidationException("type is not support..");

        if(ActionType.valueOf(event.getAction()) == null)
            throw new EventParameterValidationException("action is not support..");

        //if(isBlank(event.getContent()))
        //    throw new EventParameterValidationException("content cannot be null..");

    }

    private void handleReviewEvent(TripleUser user, EventDTO event) throws Exception {
        if(event.getAction().equals(ActionType.ADD.getCode())) {
            handleReviewEventAddAction(user, event);
        } else if(event.getAction().equals(ActionType.MOD.getCode())) {
            handleReviewEventModAction(user, event);
        } else if(event.getAction().equals(ActionType.DELETE.getCode())) {
            handleReviewEventDeleteAction(user, event);
        }
    }

    private void handleReviewEventAddAction(TripleUser user, EventDTO event) throws Exception {
        Long estimatePoint = estimatePoint(event);
        Long reviewId = reviewService.addReview(event.getUserId(), event.getContent(), estimatePoint);
        userService.addPoint(event.getUserId(), user.getUser().getPoint() + estimatePoint);
        handleImages(reviewId, event);
    }

    private void handleReviewEventModAction(TripleUser user, EventDTO event) throws Exception {
        Long estimatePoint = estimatePoint(event);
        Long oldReviewPoint = reviewService.modReview(event.getReviewId(), event.getContent(), estimatePoint);
        userService.addPoint(event.getUserId(), user.getUser().getPoint() - oldReviewPoint + estimatePoint);
        handleImages(event.getReviewId(), event);
    }

    private void handleReviewEventDeleteAction(TripleUser user, EventDTO event) throws Exception {
        Long oldReviewPoint = reviewService.deleteReview(event.getUserId(), event.getReviewId());
        userService.addPoint(event.getUserId(), user.getUser().getPoint() - oldReviewPoint);
    }

    private void handleImages(Long serviceTypeId, EventDTO event) throws Exception {
        if(event.getAttachedPhotoIds() != null && event.getAttachedPhotoIds().size() > 0) {
            for(Long imageId : event.getAttachedPhotoIds())
                imageService.updateServiceTypeId(serviceTypeId, imageId);
        }
    }

    private Long estimatePoint(EventDTO event) {
        Long point = 0L;
        if(event.getContent() != null && event.getContent().length() >= 1) {
            point++;
        }
        if(event.getAttachedPhotoIds() != null && event.getAttachedPhotoIds().size() >= 1) {
            point++;
        }
        //TODO should check new place
        return point;
    }
}
