package com.app.budget.core.services;

import com.app.budget.infrastructure.persistence.entities.UserEntity;
import com.app.budget.infrastructure.persistence.repositories.BoardRepository;
import com.app.budget.infrastructure.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository jdbcBoardRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository jdbcUserRepository;

    public Long save(String token) {
        String jwt = token.replace("Bearer ", "");
        String email = tokenService.extractEmailFromToken(jwt);
        UserEntity loggedUser = (UserEntity) jdbcUserRepository.getUserDetailsByEmail(email);

        Long userId = Optional.ofNullable(loggedUser.getId()).orElseThrow(() -> new RuntimeException());

        Long savedBoardId = jdbcBoardRepository.save(userId);
        return savedBoardId;
    }
}
