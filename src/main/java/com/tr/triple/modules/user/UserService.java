package com.tr.triple.modules.user;

import com.tr.triple.config.jwt.JwtToken;
import com.tr.triple.config.jwt.JwtTokenProvider;
import com.tr.triple.modules.common.CommonError;
import com.tr.triple.modules.common.ErrorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    public void addUser(UserInfo user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public UserInfo getUser(String userName) {
        UserInfo user = userRepository.findByUserName(userName);
        user.setPassword(null);
        return user;
    }

    public Object login(TripleUser login) {
        String userName = login.getUsername();
        UserInfo user = getUser(userName);
        if (user == null) {
            return ErrorResponseDTO.builder()
                    .errorCode(UserError.INVALID_USERID.getCode())
                    .errorMessage(UserError.INVALID_USERID.getDescription()).build();
        }

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, login.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (e instanceof BadCredentialsException)
                return ErrorResponseDTO.builder()
                        .errorCode(UserError.WRONG_PASSWORD.getCode())
                        .errorMessage(UserError.WRONG_PASSWORD.getDescription()).build();
            else
                return ErrorResponseDTO.builder()
                        .errorCode(CommonError.SERVER_ERROR.getCode())
                        .errorMessage(CommonError.SERVER_ERROR.getDescription()).build();
        }

        JwtToken jwt = jwtTokenProvider.createToken(userName);
        return jwt.getAccessToken();
    }

    public void logOut(HttpServletRequest request, HttpServletResponse response) {
        jwtTokenProvider.clearCookie(request, response);
    }

}
