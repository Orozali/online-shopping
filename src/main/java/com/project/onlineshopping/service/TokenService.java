package com.project.onlineshopping.service;

import com.project.onlineshopping.exceptions.AuthenticationFailException;
import com.project.onlineshopping.model.AuthenticationToken;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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
//    public AuthenticationToken findByUserId(int id){
//        return tokenRepository.findByUserId(id);
//    }

//    public AuthenticationToken findByUser(UserInfo user) {
//        return tokenRepository.findByUser(user);
//    }
    public UserInfo getUser(String token){
        return tokenRepository.findByToken(token).get().getUser();
    }
    public void authenticate(String token) {
        Optional<AuthenticationToken> token1 = tokenRepository.findByToken(token);
        if(token1.isEmpty()){
            throw new AuthenticationFailException("Такой токен не найден!");
        }
        if(Objects.isNull(getUser(token))){
            throw new AuthenticationFailException("Токен не валидный!");
        }
    }

}
