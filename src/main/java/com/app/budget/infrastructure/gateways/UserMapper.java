package com.app.budget.infrastructure.gateways;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapper {

    public UserEntity toEntity(User domain) {
        Long id = Optional.ofNullable(domain.getId()).orElse(null);
        return new UserEntity(id, domain.getName(), domain.getEmail(), domain.getPassword(), domain.getRole());
    }

    public UserEntity toEntity(Long id, String name, String email, String password, UserRole role) {
        id = Optional.ofNullable(id).orElse(null);
        return new UserEntity(id, name, email, password, role);
    }

    public User toDomain(UserEntity entity) {
        Long id = Optional.ofNullable(entity.getId()).orElse(null);
        return new User(id, entity.getName(), entity.getEmail(), entity.getPassword(), entity.getRole());
    }

}
