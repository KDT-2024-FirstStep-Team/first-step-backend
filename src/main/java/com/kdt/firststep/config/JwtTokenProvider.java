package com.kdt.firststep.config;


import com.kdt.firststep.user.dto.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final Key key;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60;    // 1시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 3;  // 3일

    public TokenDTO generateToken(String email, String userNickname, List<String> roles) {
        long now = System.currentTimeMillis();

        Date accessTokenExpiredTime = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredTime = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("userNickname", userNickname)
                .claim("authorities", roles)
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpiredTime)
                .signWith(key)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(email)
                .claim("userNickname", userNickname)
                .claim("authorities", roles)
                .setIssuedAt(new Date())
                .setExpiration(refreshTokenExpiredTime)
                .signWith(key)
                .compact();

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiredTime(ACCESS_TOKEN_EXPIRE_TIME)
                .refreshTokenExpiredTime(REFRESH_TOKEN_EXPIRE_TIME)
                .build();

    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = claims.get("authorities", List.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String getUserEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
