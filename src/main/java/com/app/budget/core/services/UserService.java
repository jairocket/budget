package com.app.budget.core.services;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.core.exceptions.UserException;
import com.app.budget.infrastructure.gateways.UserMapper;
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

    public Long save(User user) {
        if (this.userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserException("User already exists");
        }

        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        UserEntity entity = userMapper.toEntity(user, encryptedPassword);
        Long savedUserEntityId = jdbcUserRepository.save(entity);
        return savedUserEntityId;
    }

    public User register(String name, String email, String password, UserRole role) {
        if (this.userRepository.findByEmail(email) != null) {
            throw new UserException("User already exists");
        }

        String encryptedPassword = passwordEncoder.encode(password);

        User user = new User(name, email, password, role);

        UserEntity entity = userMapper.toEntity(user, encryptedPassword);

        UserEntity newUser = this.userRepository.save(entity);

        return userMapper.toDomain(newUser);
    }

    public List<UserEntity> findAll() {
        return this.userRepository.findAll();
    }

    public User updatePassword(String token, String password) {
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(jwt);

        UserEntity entity = (UserEntity) this.userRepository.findByEmail(email);

        User user = userMapper.toDomain(entity);
        user.setPassword(password);

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        UserEntity updatedEntity = userMapper.toEntity(user, encryptedPassword);

        UserEntity updatedUser = userRepository.save(updatedEntity);

        return userMapper.toDomain(updatedUser);
    }

    public User updateName(String token, String name) {
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(jwt);

        UserEntity entity = (UserEntity) this.userRepository.findByEmail(email);

        User user = userMapper.toDomain(entity);
        user.setName(name);

        UserEntity updatedEntity = userMapper.toEntity(user, entity.getPassword());
        UserEntity updatedUser = userRepository.save(updatedEntity);

        return userMapper.toDomain(updatedUser);
    }

    public User updateRole(Long userId, String role) {
        UserEntity entity = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UserException("Could not update user role. User not found"));

        User user = this.userMapper.toDomain(entity);
        user.setRole(UserRole.valueOf(role));
        UserEntity updatedUserEntity = userMapper.toEntity(user, entity.getPassword());
        UserEntity updatedUser = userRepository.save(updatedUserEntity);

        return userMapper.toDomain(updatedUser);
    }

    public List<User> getAll() {
        List<UserEntity> userEntityList = jdbcUserRepository.getAllUsers();
        return userEntityList.stream().map(userEntity -> userMapper.toDomain(userEntity)).toList();
    }

}
