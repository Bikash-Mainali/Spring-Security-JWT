package com.oauth2security.controller;

import com.oauth2security.dto.UserDTO;
import com.oauth2security.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/19/23
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    UserController(final UserService userService){
        this.userService = userService;
    }

    @GetMapping
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllUser(Pageable pageable) {
        List<UserDTO> userDTOList = userService.getAllUsers(pageable);
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        UserDTO userDTO = userService.findUserByEmail(email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
