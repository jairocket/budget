package com.app.budget.infrastructure.persistence.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository {
    Long save(Long userId);
}
