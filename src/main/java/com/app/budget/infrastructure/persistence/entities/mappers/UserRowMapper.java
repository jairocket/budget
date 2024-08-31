package com.app.budget.infrastructure.persistence.entities.mappers;

import com.app.budget.core.enums.UserRole;
import com.app.budget.infrastructure.persistence.entities.JDBCUser;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<JDBCUser> {
    @Override
    public JDBCUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRole userRole = rs.getInt("role") == 0 ? UserRole.ADMIN : UserRole.USER;

        JDBCUser user = new JDBCUser(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                userRole
        );

        return user;
    }
}