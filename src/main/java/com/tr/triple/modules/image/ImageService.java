package com.tr.triple.modules.image;

import com.tr.triple.modules.code.ServiceType;
import com.tr.triple.modules.service.ServiceRepository;
import com.tr.triple.modules.user.TripleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ServiceRepository serviceRepository;

    private final static String UPLOAD_FILE_PATH = "C:\\upload\\";

    public void updateServiceTypeId(Long serviceTypeId, Long imageId) throws Exception {
        Optional<Image> image = imageRepository.findById(imageId);
        if(image.isPresent()) {
            image.get().setServiceTypeId(serviceTypeId);
            imageRepository.save(image.get());
        } else {
            throw new Exception("not found image...");
        }
    }

    public List<Long> getImagesIdForTempCode(TripleUser user, String serviceType) throws Exception {
        ServiceType service = ServiceType.valueOf(serviceType);
        Long serviceId = serviceRepository.findByServiceType(Integer.parseInt(service.getCode())).getServiceId();
        if(serviceId == null) {
            throw new Exception("not found service ..");
        }
        List<Image> images = imageRepository.findByServiceIdAndUserId(serviceId, user.getUser().getUserId());
        //return images.stream().parallel().filter( image -> image.getServiceTypeId() == null).collect(Collectors.toList());

        List<Long> imageIds = new ArrayList<>();
        for(Image image : images) {
            if(image.getServiceTypeId() == null)
                imageIds.add(image.getImageId());
        }
        return imageIds;
    }

    public List<Image> getImages(TripleUser user, String serviceType) throws Exception {
        ServiceType service = ServiceType.valueOf(serviceType);
        Long serviceId = serviceRepository.findByServiceType(Integer.parseInt(service.getCode())).getServiceId();
        if(serviceId == null) {
            throw new Exception("not found service ..");
        }
        return imageRepository.findByServiceIdAndUserId(serviceId, user.getUser().getUserId());
    }

    public Long saveImage(TripleUser user, String serviceType, MultipartFile file) throws Exception {
        String imagePath = saveImageToHDD(UPLOAD_FILE_PATH + serviceType, file);
        ServiceType service = ServiceType.valueOf(serviceType);
        Long serviceId = serviceRepository.findByServiceType(Integer.parseInt(service.getCode())).getServiceId();
        if(serviceId == null) {
            throw new Exception("not found service ..");
        }
        return imageRepository.save(Image.builder()
                .userId(user.getUser().getUserId())
                .serviceId(serviceId)
                .imagePath(imagePath).build()).getImageId();
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
