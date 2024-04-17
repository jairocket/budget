package com.app.budget.core.services;

import com.app.budget.core.domain.User;
import com.app.budget.core.exceptions.UserException;
import com.app.budget.infrastructure.gateways.UserMapper;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import com.app.budget.infrastructure.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        UserEntity entity = userMapper.toEntity(user.getId(), user.getName(), user.getEmail(), encryptedPassword, user.getRole());
        if (this.userRepository.findByEmail(entity.getEmail()) != null) {
            throw new UserException("User already exists");
        }

        UserEntity newEntity = this.userRepository.save(entity);
        return userMapper.toDomain(newEntity);
    }


}
