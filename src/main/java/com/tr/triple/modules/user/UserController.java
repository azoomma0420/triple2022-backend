package com.tr.triple.modules.user;

import com.tr.triple.config.annotation.LoginUser;
import com.tr.triple.modules.common.ErrorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping(value = {"/signup"})
    public ResponseEntity<?> addUser(@RequestBody UserInfo addUser) {
        userService.addUser(addUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = {"/login"})
    public ResponseEntity<?> login(@RequestBody TripleUser login) {

        Object retObject = userService.login(login);
        if(retObject instanceof ErrorResponseDTO )
            return new ResponseEntity<>(retObject, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(retObject, HttpStatus.OK);
    }

    @GetMapping(value = {"/logout"})
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.logOut(request, response);
    }

    @GetMapping(value = {"/session"})
    public ResponseEntity<?> session(@LoginUser TripleUser user) {
        if(user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        UserInfo responseUser = user.getUser();
        responseUser.setPassword(null);
        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }
}
