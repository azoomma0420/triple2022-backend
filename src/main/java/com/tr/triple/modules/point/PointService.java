package com.tr.triple.modules.point;


import com.tr.triple.modules.user.TripleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    public Long getPoint(Long userId) {
        if(userId != null) {
            Point point = pointRepository.findByUserId(userId);
            if (point != null) {
                return point.getPoint();
            }
        }
        return 0L;
    }

    public boolean addPoint(Long userId, Long point) {
        Point oldPoint = pointRepository.findByUserId(userId);
        if(oldPoint != null) {
            oldPoint.setPoint(oldPoint.getPoint() + point);
            pointRepository.save(oldPoint);
        } else {
            pointRepository.save(Point.builder()
                    .userId(userId)
                    .point(point).build());
        }
        return true;
    }

    public boolean modPoint(Long userId, Long point) {
        Point oldPoint = pointRepository.findByUserId(userId);
        if(oldPoint != null) {
            oldPoint.setPoint(oldPoint.getPoint() + point);
            pointRepository.save(oldPoint);
            return true;
        } else {
            return false;
        }
    }

    public boolean deletePoint(Long userId) {
        Point oldPoint = pointRepository.findByUserId(userId);
        if(oldPoint != null) {
            pointRepository.delete(oldPoint);
            return true;
        } else {
            return false;
        }
    }
}
