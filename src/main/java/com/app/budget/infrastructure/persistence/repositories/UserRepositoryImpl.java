package com.app.budget.infrastructure.persistence.repositories;


import com.app.budget.infrastructure.persistence.entities.JDBCUser;
import com.app.budget.infrastructure.persistence.entities.mappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UserRowMapper userRowMapper;
    
    public List<JDBCUser> getAllUsers() {
        var users = jdbcTemplate.query(
                "SELECT id, name, email, password, role FROM USERS;",
                userRowMapper
        );
        return users;
    }
}
