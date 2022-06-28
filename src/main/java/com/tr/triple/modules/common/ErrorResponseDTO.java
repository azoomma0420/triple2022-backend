package com.tr.triple.modules.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
    private String errorCode;
    private String errorMessage;
}
