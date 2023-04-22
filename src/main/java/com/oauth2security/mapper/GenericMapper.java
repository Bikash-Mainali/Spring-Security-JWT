package com.oauth2security.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/19/23
 */
public interface GenericMapper<E, D> {

    E toEntity(D dto);

    D toDTO(E entity);

    default List<E> toEntityList(final List<D> dtos) {
        if (!dtos.isEmpty())
            return dtos.stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList());
        return null;
    }

    default List<D> toDTOList(final List<E> entityList) {
        if (!entityList.isEmpty())
            return entityList.stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        return null;
    }

}
