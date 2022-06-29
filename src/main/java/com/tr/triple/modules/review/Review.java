package com.tr.triple.modules.review;

import com.tr.triple.modules.user.UserReview;
import lombok.*;


import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String content;
}
