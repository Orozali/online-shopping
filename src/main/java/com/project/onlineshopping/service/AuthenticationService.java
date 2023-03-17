package com.project.onlineshopping.service;

import com.project.onlineshopping.dto.SignInDTO;
import com.project.onlineshopping.dto.SignInResponseDTO;
import com.project.onlineshopping.dto.SignUpResponseDTO;
import com.project.onlineshopping.exceptions.UserAlreadyExistException;
import com.project.onlineshopping.model.AuthenticationToken;
import com.project.onlineshopping.model.TokenType;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.repository.TokenRepository;
import com.project.onlineshopping.repository.UserRepository;
import com.project.onlineshopping.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public SignUpResponseDTO register(UserInfo request) {
    Optional<UserInfo> findByEmail = repository.findByEmail(request.getEmail());
    if(findByEmail.isPresent()){
      throw new UserAlreadyExistException("Пользователь с таким электронным адресам уже существует!");
    }
    var user = UserInfo.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role("ROLE_ADMIN")
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    return SignUpResponseDTO.builder()
            .message("Success!")
        .token(jwtToken)
        .build();
  }

  public SignInResponseDTO authenticate(SignInDTO request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return SignInResponseDTO.builder()
            .status("Success!")
        .token(jwtToken)
        .build();
  }

  private void saveUserToken(UserInfo user, String jwtToken) {
    var token = AuthenticationToken.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
            .createdAt(new Date())
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(UserInfo user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}