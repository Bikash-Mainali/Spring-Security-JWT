package com.oauth2security.mapper;

import com.oauth2security.domain.Role;
import com.oauth2security.domain.RoleDTO;
import com.oauth2security.domain.UserEntity;
import com.oauth2security.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/19/23
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserEntity, UserDTO> {
    @Override
    UserDTO toDTO(UserEntity user);

    @Override
    UserEntity toEntity(UserDTO userDTO);

    @Override
    List<UserEntity> toEntityList(List<UserDTO> dtos);

    @Override
    List<UserDTO> toDTOList(List<UserEntity> users);
}
