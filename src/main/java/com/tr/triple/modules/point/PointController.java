package com.tr.triple.modules.point;


import com.tr.triple.config.annotation.LoginUser;
import com.tr.triple.modules.common.CommonError;
import com.tr.triple.modules.common.ErrorResponseDTO;
import com.tr.triple.modules.user.TripleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @GetMapping(value = {"/point"})
    public ResponseEntity<?> getPoint(@LoginUser TripleUser user) {
        if(user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<>(pointService.getPoint(user.getUser().getUserId()), HttpStatus.OK);
    }
}
