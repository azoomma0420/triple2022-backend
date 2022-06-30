package com.tr.triple.modules.image;

import com.tr.triple.modules.code.ServiceType;
import com.tr.triple.modules.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ServiceRepository serviceRepository;

    public Long saveImage(String serviceType, MultipartFile file) {
        if(serviceType.equals(ServiceType.REVIEW.name())) {
            Long serviceId = serviceRepository.findByServiceType(
                    Integer.parseInt(ServiceType.REVIEW.getCode())).getServiceId();
            return imageRepository.save(Image.builder()
                                            .serviceId(serviceId)
                                            .build()).getImageId();
        }
        return 0L;
    }


}
