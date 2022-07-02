package com.tr.triple.modules.event;


import com.tr.triple.config.annotation.LoginUser;
import com.tr.triple.modules.common.code.CommonError;
import com.tr.triple.modules.common.ErrorResponseDTO;
import com.tr.triple.modules.user.TripleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping(value = {"/event"})
    public ResponseEntity<?> event(@LoginUser TripleUser user, @RequestBody EventDTO event) {
        if(user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            System.out.println(event.toString());
            eventService.handleEvent(user, event);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof EventParameterValidationException)
                return new ResponseEntity<>(ErrorResponseDTO.builder()
                        .errorCode(CommonError.CLIENT_ERROR.getCode())
                        .errorMessage(CommonError.CLIENT_ERROR.getDescription()).build(), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(ErrorResponseDTO.builder()
                        .errorCode(CommonError.SERVER_ERROR.getCode())
                        .errorMessage(CommonError.SERVER_ERROR.getDescription()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
