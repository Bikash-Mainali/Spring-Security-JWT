package com.oauth2security.service;

import com.oauth2security.domain.UserEntity;
import com.oauth2security.dto.UserDTO;
import com.oauth2security.mapper.UserMapper;
import com.oauth2security.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);
        return userMapper.toDTOList(page.getContent());
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email).get();
        return userMapper.toDTO(user);
    }

    @Override
    public Optional<UserDTO> findOptionalUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return null;
    }
}
