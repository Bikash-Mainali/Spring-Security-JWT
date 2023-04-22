package com.oauth2security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oauth2security.domain.Role;
import com.oauth2security.domain.UserEntity;
import com.oauth2security.dto.AuthResponseDTO;
import com.oauth2security.dto.LoginDTO;
import com.oauth2security.dto.RegisterDTO;
import com.oauth2security.exceptions.EntityAlreadyExistException;
import com.oauth2security.repository.RoleRepository;
import com.oauth2security.repository.UserRepository;
import com.oauth2security.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/19/23
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;
    private RoleRepository roleRepository;

    @Value("${role.default}")
    private String defaultRole;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JWTGenerator jwtGenerator,
                          RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) throws JsonProcessingException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String token = jwtGenerator.generateJWTToken(authentication);
        AuthResponseDTO authResponseDTO =  AuthResponseDTO.builder()
                .expiresIn(expiration)
                // TODO: 4/21/23 create refresh token and store in db  
                .accessToken(token)
                .build();
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new EntityAlreadyExistException("user name is already taken");
        }

        Role role = Role.builder().name(defaultRole).build();
        UserEntity user = new UserEntity();
                user.setEmail(registerDto.getEmail());
                user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
                // TODO: 4/20/23 do some custom roles set up
                user.setRoles(Collections.singleton(role));

        userRepository.save(user);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }
}
