package com.tr.triple.modules.review;


import com.tr.triple.config.annotation.LoginUser;
import com.tr.triple.modules.user.TripleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping(value = {"/reviews"})
    public ResponseEntity<?> getReviews(@LoginUser TripleUser user) {
        if(user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<>(reviewService.getReviews(user.getUser().getUserId()), HttpStatus.OK);
    }
}
