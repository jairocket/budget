package com.app.budget.core.services;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.core.exceptions.UserException;
import com.app.budget.infrastructure.gateways.UserMapper;
import com.app.budget.infrastructure.persistence.entities.JDBCUser;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import com.app.budget.infrastructure.persistence.repositories.UserRepository;
import com.app.budget.infrastructure.persistence.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepositoryImpl jdbcUserRepository;

    public UserEntity register(String name, String email, String password, UserRole role) {
        if (this.userRepository.findByEmail(email) != null) {
            throw new UserException("User already exists");
        }

        String encryptedPassword = passwordEncoder.encode(password);

        User user = new User(name, email, password, role);

        UserEntity entity = userMapper.toEntity(user, encryptedPassword);

        return this.userRepository.save(entity);
    }

    public List<UserEntity> findAll() {
        return this.userRepository.findAll();
    }

    public UserEntity updatePassword(String token, String password) {
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(jwt);

        UserEntity entity = (UserEntity) this.userRepository.findByEmail(email);

        User user = userMapper.toDomain(entity);
        user.setPassword(password);

        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        UserEntity updatedEntity = userMapper.toEntity(user, encryptedPassword);

        return userRepository.save(updatedEntity);
    }

    public UserEntity updateName(String token, String name) {
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(jwt);

        UserEntity entity = (UserEntity) this.userRepository.findByEmail(email);

        User user = userMapper.toDomain(entity);
        user.setName(name);

        UserEntity updatedEntity = userMapper.toEntity(user, entity.getPassword());

        return userRepository.save(updatedEntity);
    }

    public UserEntity updateRole(Long userId, String role) {
        UserEntity entity = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UserException("Could not update user role. User not found"));

        User user = this.userMapper.toDomain(entity);
        user.setRole(UserRole.valueOf(role));
        UserEntity updatedUserEntity = userMapper.toEntity(user, entity.getPassword());

        return userRepository.save(updatedUserEntity);
    }

    public List<JDBCUser> getAll() {
        return jdbcUserRepository.getAllUsers();
    }

}
