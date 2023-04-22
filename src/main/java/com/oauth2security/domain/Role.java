package com.oauth2security.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/21/23
 */
@Data
@Builder
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
}
