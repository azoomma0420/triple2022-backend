package com.tr.triple.modules.review;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> search(Long userId);
}
