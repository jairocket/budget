package com.app.budget.infrastructure.persistence.repositories;


import com.app.budget.infrastructure.persistence.entities.UserEntity;
import com.app.budget.infrastructure.persistence.entities.mappers.UserDetailsRowMapper;
import com.app.budget.infrastructure.persistence.entities.mappers.UserRowMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.userdetails.UserDetails;
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
    private UserDetailsRowMapper userDetailsRowMapper;

    @Autowired
    private HikariDataSource dataSource;

    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = jdbcTemplate.query(
                "SELECT id, name, email, password, role FROM USERS;",
                userRowMapper
        );
        return users;
    }

    public UserDetails getUserDetailsByEmail(String email) {
        List<UserDetails> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE email = :email ",
                new MapSqlParameterSource("email", email),
                userDetailsRowMapper
        );

        if (users.size() > 1)
            throw new RuntimeException("Something wrong with this shit");

        if (users.isEmpty())
            return null;

        return users.get(0);
    }

    public UserEntity getUserById(Long id) {
        List<UserEntity> users = jdbcTemplate.query(
                "SELECT * FROM USERS WHERE id = :id ",
                new MapSqlParameterSource("id", id),
                userRowMapper
        );

        if (users.size() > 1)
            throw new RuntimeException("Something wrong with this shit");

        if (users.isEmpty())
            return null;

        return users.get(0);
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

    public void update(UserEntity userEntity) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource();

        sqlParameters.addValue("id", userEntity.getId());
        sqlParameters.addValue("name", userEntity.getName());
        sqlParameters.addValue("email", userEntity.getEmail());
        sqlParameters.addValue("password", userEntity.getPassword());
        sqlParameters.addValue("role", userEntity.getRole().ordinal());

        jdbcTemplate.update(
                "UPDATE users " +
                        "SET " +
                        "name = :name, " +
                        "password = :password, " +
                        "role = :role  " +
                        "WHERE email = :email",
                sqlParameters
        );
    }

}
