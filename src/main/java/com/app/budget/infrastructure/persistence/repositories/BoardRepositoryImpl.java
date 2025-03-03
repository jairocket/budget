package com.app.budget.infrastructure.persistence.repositories;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BoardRepositoryImpl implements BoardRepository {
    @Autowired
    private HikariDataSource dataSource;

    @Override
    public Long save(Long userId) {
        Map<String, Object> boardProperties = new HashMap<>();

        boardProperties.put("user_id", userId);

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("boards")
                .usingGeneratedKeyColumns("id");

        Long id = simpleJdbcInsert.executeAndReturnKey(boardProperties).longValue();

        return id;
    }
}
