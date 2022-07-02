package com.tr.triple.modules.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionDTO {
    private User user;
}
