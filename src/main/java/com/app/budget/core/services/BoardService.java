package com.app.budget.core.services;

import com.app.budget.infrastructure.persistence.repositories.BoardRepository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository jdbcBoardRepository;

    public Long save(Long loggedUserId) {
        Long savedBoardId = jdbcBoardRepository.save(loggedUserId);
        return savedBoardId;
    }
}
