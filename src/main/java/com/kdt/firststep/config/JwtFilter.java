package com.kdt.firststep.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final JwtProperties jwtProperties;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(jwtProperties.getTokenHeader()); // e.g., "Authorization"
        String token = null;
        if (header != null && header.startsWith(jwtProperties.getTokenPrefix())) {
            token = header.substring((jwtProperties.getTokenPrefix()).length());
        }
        if (token != null && tokenProvider.validateAccessToken(token)) {
                String userEmail = tokenProvider.getUserEmail(token);
                List<GrantedAuthority> authorities = tokenProvider.getAuthorities(token);
                User user = new User(userEmail, "", authorities);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                response.addHeader(jwtProperties.getTokenHeader(), jwtProperties.getTokenPrefix() + token);
        }
           else if (token != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            return;
        }

        chain.doFilter(request, response);
    }
}
