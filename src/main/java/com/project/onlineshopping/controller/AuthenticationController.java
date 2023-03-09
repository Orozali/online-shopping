package com.project.onlineshopping.controller;

import com.project.onlineshopping.dto.SignInResponseDTO;
import com.project.onlineshopping.dto.SignUpResponseDTO;
import com.project.onlineshopping.dto.SignInDTO;
import com.project.onlineshopping.dto.UserInfoDTO;
import com.project.onlineshopping.exceptions.AuthenticationFailException;
import com.project.onlineshopping.exceptions.ErrorMessage;
import com.project.onlineshopping.exceptions.TokenNullException;
import com.project.onlineshopping.exceptions.UserAlreadyExistException;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody UserInfoDTO userDTO){
        UserInfo user = convert(userDTO);
        userService.signUp(user);
        return new ResponseEntity<>(new SignUpResponseDTO("success","Пользователь успешно зарегистрировалось!"),HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInDTO signInDTO) throws NoSuchAlgorithmException {
            return new ResponseEntity<>(userService.signIn(signInDTO),HttpStatus.OK);
    }

    public UserInfo convert(UserInfoDTO userDTO){
        return modelMapper.map(userDTO, UserInfo.class);
    }
    public UserInfo convert(SignInDTO userDTO){
        return modelMapper.map(userDTO, UserInfo.class);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> userAlreadyExist(UserAlreadyExistException e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> authenticationFail(AuthenticationFailException e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> tokenFail(TokenNullException e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
