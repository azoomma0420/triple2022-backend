package com.tr.triple.modules.code;

import com.tr.triple.modules.common.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType implements Code {

    ADD("ADD", "생성"),
    MOD("MOD", "수정"),
    DELETE("DELETE", "삭제");

    private final String code;
    private final String description;
}
