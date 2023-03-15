package com.project.onlineshopping.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponseDTO {
    private String message;
    private String token;
}
