package com.tr.triple.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String accessToken = jwtTokenProvider.resolveCookie(httpServletRequest);
        //String refreshToken = null;
        String userName = null;
        if (accessToken == null && authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7);
        }

        if( accessToken != null ) {
            try {
                userName = jwtTokenProvider.extractUsername(accessToken);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                jwtTokenProvider.clearCookie(httpServletRequest, httpServletResponse);
            }
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(accessToken));
            } else {
                SecurityContextHolder.clearContext();
                jwtTokenProvider.clearCookie(httpServletRequest, httpServletResponse);
            }
        } else {
            SecurityContextHolder.clearContext();
            jwtTokenProvider.clearCookie(httpServletRequest, httpServletResponse);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
