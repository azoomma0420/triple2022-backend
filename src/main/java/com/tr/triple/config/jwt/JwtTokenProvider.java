package com.tr.triple.config.jwt;

import com.tr.triple.config.Constants;
import com.tr.triple.config.misc.CustomUserDetailsService;
import com.tr.triple.modules.user.TripleUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final CustomUserDetailsService loginService;

    @Value("${jwt.secret}")
    private String secretKey;

    private long ACCESS_VALIDITY_IN_MILLISECONDS = 3600000;

    private long REFRESH_VALIDITY_IN_MILLISECONDS = 604800000;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        final String username = extractUsername(token);
        TripleUser userDetails = (TripleUser) loginService.loadUserByUsername(username);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public JwtToken createToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);

        Date now = new Date();
        Date accessDate = new Date(now.getTime() + ACCESS_VALIDITY_IN_MILLISECONDS);
        Date refreshDate = new Date(now.getTime() + REFRESH_VALIDITY_IN_MILLISECONDS);

        return JwtToken.builder()
                .accessToken(this.generateToken(claims, now, accessDate))
                .refreshToken(this.generateToken(claims, now, refreshDate))
                .build();
    }

    private String generateToken(Map<String, Object> claims, Date now, Date expirationDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public void createCookie(HttpServletResponse response, String token) {
        /*ResponseCookie cookie = ResponseCookie.from(Constants.ACCESSTOKEN, token)
                .domain("http://localhost:8091/")
                .httpOnly(true)
                .sameSite("lax")
                .maxAge(ACCESS_VALIDITY_IN_MILLISECONDS)
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());*/
        Cookie cookie = new Cookie(Constants.TRIPLE_SID, token);
        //cookie.setDomain("localhost:8091");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1*60*60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String resolveCookie(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.TRIPLE_SID)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        if( request!=null && request.getCookies() != null)
            Arrays.asList(request.getCookies())
                    .stream()
                    .filter(cookie -> cookie.getName().equals(Constants.TRIPLE_SID))
                    .findFirst()
                    .ifPresent(c -> {
                        c.setMaxAge(0);
                        c.setPath("/");
                        response.addCookie(c);
                    });
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = loginService.loadUserByUsername(this.getClaims(token, "sub"));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Authentication getAuthenticationForDev() {
        UserDetails userDetails = loginService.loadUserByUsername("test004");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getClaims(String token, String key) {
        return this.extractAllClaims(token)
                .get(key, String.class);
    }
}

