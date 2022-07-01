package com.tr.triple.modules.user;

import com.tr.triple.modules.point.Point;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionDTO {
    private User user;
    private Point point;
}
