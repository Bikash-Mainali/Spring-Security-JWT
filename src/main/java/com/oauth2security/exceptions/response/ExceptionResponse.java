package com.oauth2security.exceptions.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/20/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String message;
    private int code;
    private LocalDateTime timestamp;
    private String uri;
}
