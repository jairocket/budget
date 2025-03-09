package com.app.budget.infrastructure.persistance;

import com.app.budget.infrastructure.persistence.repositories.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ActiveProfiles("test")
@SpringBootTest
public class BoardRepositoryImplTest {

    @Autowired
    BoardRepository boardRepository;
    
    @Test
    public void shouldBeAbleToSaveBoard() {
        final Long userId = 1L;
        final Long boardId = boardRepository.save(userId);

        assertInstanceOf(Long.class, boardId);

    }
}
