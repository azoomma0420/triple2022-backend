package com.tr.triple.modules.event;


import com.tr.triple.modules.code.ActionType;
import com.tr.triple.modules.code.EventType;
import com.tr.triple.modules.point.PointService;
import com.tr.triple.modules.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final PointService pointService;
    private final ReviewService reviewService;

    public boolean handleEvent(EventDTO event) {
        if(event.getType().equals(EventType.REVIEW.getCode())) {
            return handleReviewEvent(event);
        }
        return true;
    }

    private boolean handleReviewEvent(EventDTO event) {
        if(event.getAction().equals(ActionType.ADD.getCode())) {
            return handleReviewEventAddAction(event);
        } else if(event.getAction().equals(ActionType.MOD.getCode())) {
            return handleReviewEventModAction(event);
        } else if(event.getAction().equals(ActionType.DELETE.getCode())) {
            return handleReviewEventDeleteAction(event);
        }
        return false;
    }

    private boolean handleReviewEventAddAction(EventDTO event) {
        reviewService.addReview(event.getUserId(), event.getContent());
        pointService.addPoint(event.getUserId(), estimatePoint(event));
        return true;
    }

    private boolean handleReviewEventModAction(EventDTO event) {
        reviewService.modReview(event.getReviewId(), event.getContent());
        return true;
    }

    private boolean handleReviewEventDeleteAction(EventDTO event) {
        return true;
    }

    private Long estimatePoint(EventDTO event) {
        Long point = 0L;
        if(event.getContent() != null && event.getContent().length() >= 1) {
            point++;
        }
        if(event.getAttachedPhotoIds() != null && event.getAttachedPhotoIds().size() >= 1) {
            point++;
        }
        if(event.getNewPlace() == 1) {
            point++;
        }
        return point;
    }
}
