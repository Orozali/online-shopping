package com.project.onlineshopping.controller;

import com.project.onlineshopping.dto.SignInResponseDTO;
import com.project.onlineshopping.dto.SignUpResponseDTO;
import com.project.onlineshopping.dto.SignInDTO;
import com.project.onlineshopping.dto.UserInfoDTO;
import com.project.onlineshopping.exceptions.*;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.service.AuthenticationService;
import com.project.onlineshopping.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<SignUpResponseDTO> register(@RequestBody @Valid UserInfoDTO userDTO
                                                      , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError error: bindingResult.getFieldErrors()){
                stringBuilder.append(error.getDefaultMessage());
                throw new BindingResultExceptions(stringBuilder.toString());
            }
        }
        UserInfo user = convert(userDTO);
        return new ResponseEntity<>(authenticationService.register(user),HttpStatus.OK);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInDTO signInDTO) {
            return new ResponseEntity<>(authenticationService.authenticate(signInDTO),HttpStatus.OK);
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


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> tokenFail(ExpiredJwtException e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessage> bindingResultException(BindingResultExceptions e){
        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    public UserInfo convert(UserInfoDTO userDTO){
        return modelMapper.map(userDTO, UserInfo.class);
    }
}
