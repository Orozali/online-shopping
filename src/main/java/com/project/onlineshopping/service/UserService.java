package com.project.onlineshopping.service;

import com.project.onlineshopping.dto.SignInDTO;
import com.project.onlineshopping.dto.SignInResponseDTO;
import com.project.onlineshopping.dto.SignUpResponseDTO;
import com.project.onlineshopping.model.AuthenticationToken;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.repository.TokenRepository;
import com.project.onlineshopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    public Optional<UserInfo> findById(int id){
        return repository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> user = repository.findByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Пользователь не найден!");
        }
        return user.get();
    }

    public SignUpResponseDTO register(UserInfo user) {
        var userInfo = UserInfo.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role("ROLE_USER").build();
        repository.save(userInfo);
        String token = generateToken();
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setUser(userInfo);
        authenticationToken.setToken(token);
        authenticationToken.setCreatedAt(new Date());
        tokenRepository.save(authenticationToken);
        return new SignUpResponseDTO("success",token);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public SignInResponseDTO authenticate(SignInDTO signInDTO) {
        return null;
    }
}