package com.tr.triple.modules.image;


import com.tr.triple.config.annotation.LoginUser;
import com.tr.triple.modules.user.TripleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = {"/saveImage"})
    public ResponseEntity<?> saveImage(@LoginUser TripleUser user, @RequestParam("type") String serviceType, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(serviceType);

        if(user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<>(imageService.saveImage(serviceType, file), HttpStatus.OK);
    }
}
