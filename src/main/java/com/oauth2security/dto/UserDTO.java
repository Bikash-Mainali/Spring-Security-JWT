package com.oauth2security.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oauth2security.domain.Role;
import com.oauth2security.domain.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String email;
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private String password;
    private Set<RoleDTO> roles;
}
