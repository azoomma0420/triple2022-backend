package com.tr.triple.modules.common.code;

import com.tr.triple.modules.common.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonError implements Code {

    UNAUTHORIZED("EAU001", "권한이 없습니다. 관리자에게 문의 해주세요."),
    CLIENT_ERROR("EC001", "입력값을 확인 해주세요."),
    SERVER_ERROR("ES001", "서버에 장애가 발생 했습니다. 관라자에게 문의 해주세요.");

    private final String code;
    private final String description;
}
