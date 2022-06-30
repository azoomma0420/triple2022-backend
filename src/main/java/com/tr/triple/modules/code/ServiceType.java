package com.tr.triple.modules.code;

import com.tr.triple.modules.common.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType implements Code {

    REVIEW("1", "리뷰");

    private final String code;
    private final String description;
}
