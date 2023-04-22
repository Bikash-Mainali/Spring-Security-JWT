package com.oauth2security.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/19/23
 */

@Getter
@Setter
public class LoginDTO {
    private String email;
    private String password;
}
