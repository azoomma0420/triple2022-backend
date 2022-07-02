package com.tr.triple.modules.common.code;

import com.tr.triple.modules.common.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType implements Code {

    REVIEW("REVIEW", "사용자가 사용자 경험을 남길때 발생하는 이벤트");

    private final String code;
    private final String description;
}
