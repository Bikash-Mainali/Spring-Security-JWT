package com.oauth2security.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/21/23
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String name;
}
