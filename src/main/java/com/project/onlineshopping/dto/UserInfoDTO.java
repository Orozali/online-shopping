package com.project.onlineshopping.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    @NotEmpty(message = "Полья 'имя' не должен быть пустым")
    private String firstName;
    @NotEmpty(message = "Полья 'фамилия' не должен быть пустым")
    private String lastName;
    @NotEmpty(message = "Полья 'электронная почта' не должен быть пустым")
    @Email(message = "Адрес электронной почты должен содержать символ @")
    private String email;
    @NotEmpty(message = "Полья 'пароль' не должен быть пустым")
    private String password;
}
