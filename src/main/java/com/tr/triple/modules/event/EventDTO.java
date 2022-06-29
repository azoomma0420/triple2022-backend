package com.tr.triple.modules.event;

import lombok.Data;

import java.util.List;

@Data
public class EventDTO {
    private String type;
    private String action;
    private String content;
    private Long reviewId;
    private Long userId;
    private Long placeId;
    private int newPlace;
    private List<Long> attachedPhotoIds;

}
