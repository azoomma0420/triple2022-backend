package com.tr.triple.modules.review;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    List<Review> findByPlaceId(Long placeId);
    List<Review> findByUserId(Long userId);
    Review findByUserIdAndPlaceId(Long userId, Long placeId);
}
