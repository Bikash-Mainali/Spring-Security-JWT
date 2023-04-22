package com.oauth2security.service;


import com.oauth2security.dto.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // CRUD
    List<UserDTO> getAllUsers(Pageable pageable);

    UserDTO findUserByEmail(String email);

    Optional<UserDTO> findOptionalUserByEmail(String email);

    UserDTO getUserById(Long id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);
}
