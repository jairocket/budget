package com.app.budget.infrastructure.persistence.repositories;


import com.app.budget.infrastructure.persistence.entities.UserEntity;
import com.app.budget.infrastructure.persistence.entities.mappers.UserRowMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UserRowMapper userRowMapper;

    @Autowired
    private HikariDataSource dataSource;

    public List<UserEntity> getAllUsers() {
        var users = jdbcTemplate.query(
                "SELECT id, name, email, password, role FROM USERS;",
                userRowMapper
        );
        return users;
    }

    public Long save(UserEntity userEntity) {
        Map<String, Object> userProperties = new HashMap<>();

        userProperties.put("name", userEntity.getName());
        userProperties.put("email", userEntity.getEmail());
        userProperties.put("password", userEntity.getPassword());
        userProperties.put("role", userEntity.getRole().ordinal());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        Number id = simpleJdbcInsert.executeAndReturnKey(userProperties);

        return id.longValue();
    }


}
