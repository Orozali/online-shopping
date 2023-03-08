package com.project.onlineshopping.service;

import com.project.onlineshopping.model.AuthenticationToken;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {
    private final TokenRepository tokenRepository;
    @Transactional
    public void save(AuthenticationToken token) {
        tokenRepository.save(token);
    }
    public AuthenticationToken findByUserId(int id){
        return tokenRepository.findByUserId(id);
    }

    public AuthenticationToken findByUser(UserInfo user) {
        return tokenRepository.findByUser(user);
    }
}
