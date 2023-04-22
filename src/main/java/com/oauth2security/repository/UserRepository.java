package com.oauth2security.repository;

import com.oauth2security.domain.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.LOAD)
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
