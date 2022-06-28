package com.tr.triple.modules.user;

import com.tr.triple.modules.common.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserError implements Code {
    INVALID_USERID("LOGIN001", "사용자 아이디를 정확히 입력하세요."),
    WRONG_PASSWORD("LOGIN002", "비밀번호를 정확히 입력하세요."),
    USER_STATUS_INVALID("LOGIN003", "로그인 할 수 없습니다."),
    NO_PERMISSION("LOGIN004", "권한이 없습니다.");

    private final String code;
    private final String description;
}