package com.tr.triple.modules.image;

import com.tr.triple.modules.code.ServiceType;
import com.tr.triple.modules.service.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ServiceRepository serviceRepository;

    private final static String UPLOAD_FILE_PATH = "C:\\upload\\";

    public Long saveImage(String serviceType, MultipartFile file) {
        if(serviceType.equals(ServiceType.REVIEW.name())) {
            String imagePath = saveImageToHDD(UPLOAD_FILE_PATH + serviceType, file);
            Long serviceId = serviceRepository.findByServiceType(
                    Integer.parseInt(ServiceType.REVIEW.getCode())).getServiceId();
            return imageRepository.save(Image.builder()
                                            .serviceId(serviceId)
                                            .imagePath(imagePath).build()).getImageId();
        }
        return 0L;
    }

    private String saveImageToHDD(String dir, MultipartFile file) {
        File folder = new File(dir);
        if (!folder.exists()) folder.mkdirs();

        File destination = new File(dir + File.separator + file.getOriginalFilename());
        try {
            file.transferTo(destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination.getAbsolutePath();
    }

}
