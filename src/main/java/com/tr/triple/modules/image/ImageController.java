package com.tr.triple.modules.image;


import com.tr.triple.config.annotation.LoginUser;
import com.tr.triple.modules.common.code.CommonError;
import com.tr.triple.modules.common.ErrorResponseDTO;
import com.tr.triple.modules.user.TripleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = {"/saveImage"})
    public ResponseEntity<?> saveImage(@LoginUser TripleUser user, @RequestParam("type") String serviceType, @RequestParam("file") MultipartFile file) {
        if(user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            return new ResponseEntity<>(imageService.saveImage(user, serviceType, file), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorResponseDTO.builder()
                    .errorCode(CommonError.SERVER_ERROR.getCode())
                    .errorMessage(CommonError.SERVER_ERROR.getDescription()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/images"})
    public ResponseEntity<?> getImages(@LoginUser TripleUser user, @RequestParam("type") String serviceType) {
        if(user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            return new ResponseEntity<>(imageService.getImagesIdForTempCode(user, serviceType), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ErrorResponseDTO.builder()
                    .errorCode(CommonError.SERVER_ERROR.getCode())
                    .errorMessage(CommonError.SERVER_ERROR.getDescription()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
