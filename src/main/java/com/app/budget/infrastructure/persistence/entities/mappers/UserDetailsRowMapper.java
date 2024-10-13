package com.app.budget.infrastructure.persistence.entities.mappers;

import com.app.budget.core.enums.UserRole;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDetailsRowMapper implements RowMapper<UserDetails> {
    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRole userRole = rs.getInt("role") == 0 ? UserRole.ADMIN : UserRole.USER;

        UserEntity user = new UserEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                userRole
        );

        return user;
    }
}
