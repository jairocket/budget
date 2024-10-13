package com.app.budget.core.services;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.core.exceptions.UserException;
import com.app.budget.infrastructure.gateways.UserMapper;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import com.app.budget.infrastructure.persistence.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepositoryImpl jdbcUserRepository;

    public Long save(User user) {
        if (this.jdbcUserRepository.getUserDetailsByEmail(user.getEmail()) != null) {
            throw new UserException("User already exists");
        }

        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        UserEntity entity = userMapper.toEntity(user, encryptedPassword);
        Long savedUserEntityId = jdbcUserRepository.save(entity);
        return savedUserEntityId;
    }

    public void updatePassword(String token, String password) {
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(jwt);

        UserEntity entity = (UserEntity) jdbcUserRepository.getUserDetailsByEmail(email);

        User user = userMapper.toDomain(entity);
        user.setPassword(password);

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        UserEntity updatedEntity = userMapper.toEntity(user, encryptedPassword);

        jdbcUserRepository.update(updatedEntity);
    }

    public void updateName(String token, String name) {
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(jwt);

        UserEntity entity = (UserEntity) jdbcUserRepository.getUserDetailsByEmail(email);

        User user = userMapper.toDomain(entity);
        user.setName(name);

        UserEntity updatedEntity = userMapper.toEntity(user, entity.getPassword());

        jdbcUserRepository.update(updatedEntity);
    }

    public void updateRole(Long userId, String role) {
        UserEntity entity = this.jdbcUserRepository.getUserById(userId);

        if (entity == null)
            throw new UserException("Could not update user role. User not found");

        User user = this.userMapper.toDomain(entity);
        user.setRole(UserRole.valueOf(role));
        UserEntity updatedUserEntity = userMapper.toEntity(user, entity.getPassword());
        jdbcUserRepository.update(updatedUserEntity);
    }

    public List<User> getAll() {
        List<UserEntity> userEntityList = jdbcUserRepository.getAllUsers();
        return userEntityList.stream().map(userEntity -> userMapper.toDomain(userEntity)).toList();
    }

    public User getUserById(Long id) {
        UserEntity userEntity = jdbcUserRepository.getUserById(id);
        return userMapper.toDomain(userEntity);
    }

}
