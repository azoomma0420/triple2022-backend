package com.tr.triple.config.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}
