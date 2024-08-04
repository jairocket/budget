package com.app.budget.core.services;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.core.exceptions.UserException;
import com.app.budget.infrastructure.gateways.UserMapper;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import com.app.budget.infrastructure.persistence.repositories.UserRepository;
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

    public User register(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        UserEntity entity = userMapper.toEntity(user.getId(), user.getName(), user.getEmail(), encryptedPassword, user.getRole());
        if (this.userRepository.findByEmail(entity.getEmail()) != null) {
            throw new UserException("User already exists");
        }

        UserEntity newEntity = this.userRepository.save(entity);
        return userMapper.toDomain(newEntity);
    }

    public List<User> findAll() {
        List<UserEntity> userEntities = this.userRepository.findAll();
        return userEntities.stream().map(userMapper::toDomain).toList();
    }

    public User updatePassword(String token, String password) {
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(jwt);

        UserEntity entity = (UserEntity) this.userRepository.findByEmail(email);

        User user = userMapper.toDomain(entity);
        user.setPassword(password);

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        UserEntity updatedEntity = userMapper.toEntity(user.getId(), user.getName(), user.getEmail(), encryptedPassword, user.getRole());

        User updatedUser = userMapper.toDomain(userRepository.save(updatedEntity));

        return updatedUser;
    }

    public User updateName(String token, String name) {
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(jwt);

        UserEntity entity = (UserEntity) this.userRepository.findByEmail(email);

        User user = userMapper.toDomain(entity);
        user.setName(name);

        UserEntity updatedEntity = userMapper.toEntity(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRole());

        User updatedUser = userMapper.toDomain(userRepository.save(updatedEntity));

        return updatedUser;
    }

    public User updateRole(Long userId, String role) {
        UserEntity entity = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UserException("Could not update user role. User not found"));

        User user = this.userMapper.toDomain(entity);
        user.setRole(UserRole.valueOf(role));
        UserEntity updatedUserEntity = userMapper.toEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );

        return userMapper.toDomain(userRepository.save(updatedUserEntity));
    }

}
