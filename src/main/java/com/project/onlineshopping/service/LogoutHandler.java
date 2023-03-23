package com.project.onlineshopping.service;

import com.project.onlineshopping.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            return;
        }else{
            String jwt = header.substring(7);
            var token = tokenRepository.findByToken(jwt).orElse(null);
            if(token != null){
                token.setExpired(true);
                token.setRevoked(true);
                tokenRepository.save(token);
            }
        }
    }
}
