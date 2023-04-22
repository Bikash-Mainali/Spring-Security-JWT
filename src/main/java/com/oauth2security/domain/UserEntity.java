package com.oauth2security.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Set;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/19/23
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    // TODO: 4/19/23 Email validation
    private String email;

    private String password;

//    @ElementCollection
//    @CollectionTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
//    private Set<String> roles;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
