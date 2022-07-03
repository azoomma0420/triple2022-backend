package com.tr.triple.modules.image;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByServiceIdAndUserId(Long serviceId, Long userId);
    List<Image> findByServiceTypeId(Long serviceTypeId);
}
