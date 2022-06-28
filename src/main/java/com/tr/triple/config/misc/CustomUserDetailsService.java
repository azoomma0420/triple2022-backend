package com.tr.triple.config.misc;

import com.tr.triple.modules.user.TripleUser;
import com.tr.triple.modules.user.UserInfo;
import com.tr.triple.modules.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo loggingUser = userRepository.findByUserName(userName);
        if(loggingUser == null) {
            throw new UsernameNotFoundException("not found user");
        }
        return new TripleUser(loggingUser);
    }
}

