package com.project.onlineshopping.service;

import com.project.onlineshopping.dto.SignInDTO;
import com.project.onlineshopping.dto.SignInResponseDTO;
import com.project.onlineshopping.exceptions.AuthenticationFailException;
import com.project.onlineshopping.exceptions.TokenNullException;
import com.project.onlineshopping.exceptions.UserAlreadyExistException;
import com.project.onlineshopping.model.AuthenticationToken;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.repository.UserRepository;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    @Transactional
    public void signUp(UserInfo user) {
        Optional<UserInfo> takenUser = userRepository.findByEmail(user.getEmail());
        if(takenUser.isEmpty()){
            AuthenticationToken token = new AuthenticationToken(user);
            tokenService.save(token);
            try {
                user.setPassword(hashPassword(user.getPassword()));
            }catch (NoSuchAlgorithmException e){
                e.printStackTrace();
                throw new AuthenticationFailException(e.getMessage());
            }
            user.setRole("user");
            user.setToken(token);
            userRepository.save(user);
        }
        else {
            throw new UserAlreadyExistException("Пользователь с таким email адресом уже существует!");
        }
    }

    public SignInResponseDTO signIn(SignInDTO signInUser) throws NoSuchAlgorithmException {
        Optional<UserInfo> userInfo = userRepository.findByEmail(signInUser.getEmail());
        if(userInfo.isPresent()){
            String signInUserPassword = signInUser.getPassword();
            String checkPassword = hashPassword(signInUserPassword);
            if(checkPassword.equals(userInfo.get().getPassword())){
                AuthenticationToken token = userInfo.get().getToken();
                if(Objects.isNull(token)){
                    throw new TokenNullException("Токен не найден!");
                }else{
                    return new SignInResponseDTO("success",token.getToken());
                }
            }else{
                throw new AuthenticationFailException("Неверный пароль или логин!");
            }
        }else{
            throw new AuthenticationFailException("Неверный пароль или логин!");
        }
    }


    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

}
