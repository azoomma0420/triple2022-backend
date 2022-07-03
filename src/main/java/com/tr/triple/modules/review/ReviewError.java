package com.tr.triple.modules.review;

import com.tr.triple.modules.common.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewError implements Code {
    DUPLICATED_PLACE_ID("REV001", "한 사용자는 장소마다 리뷰를 1개만 작성 할 수 있습니다.");

    private final String code;
    private final String description;
}