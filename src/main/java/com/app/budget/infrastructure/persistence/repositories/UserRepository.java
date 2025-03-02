package com.app.budget.infrastructure.persistence.repositories;

import com.app.budget.infrastructure.persistence.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    UserDetails getUserDetailsByEmail(String email);

    List<UserEntity> getAllUsers();

    UserEntity getUserById(Long id);

    Long save(UserEntity userEntity);

    void update(UserEntity userEntity);

}
