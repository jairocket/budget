package com.app.budget.infrastructure.persistence.repositories.BoardRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository {
    Long save(Long userId);
}
