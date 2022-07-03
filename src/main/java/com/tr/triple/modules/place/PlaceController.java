package com.tr.triple.modules.place;


import com.tr.triple.config.annotation.LoginUser;
import com.tr.triple.modules.common.code.CommonError;
import com.tr.triple.modules.common.ErrorResponseDTO;
import com.tr.triple.modules.user.TripleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping(value = {"/savePlace"})
    public ResponseEntity<?> savePlace(@LoginUser TripleUser user, Double latitude, Double longitude) {
        if(user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            return new ResponseEntity<>(placeService.savePlace(latitude, longitude), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ErrorResponseDTO.builder()
                    .errorCode(CommonError.SERVER_ERROR.getCode())
                    .errorMessage(CommonError.SERVER_ERROR.getDescription()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
