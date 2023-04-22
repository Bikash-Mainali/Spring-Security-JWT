package com.oauth2security.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/20/23
 */
@Data
@Builder
public class AuthResponseDTO {
    private String accessToken;

    @Builder.Default
    private String tokenType = "Bearer";
    private Long expiresIn;
    private String refreshToken;
}
