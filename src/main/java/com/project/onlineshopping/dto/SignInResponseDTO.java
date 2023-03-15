package com.project.onlineshopping.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class SignInResponseDTO {
    private String status;
    private String token;
}
