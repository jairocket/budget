package com.app.budget.infrastructure.controllers;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.core.services.TokenService;
import com.app.budget.infrastructure.AbstractIntegrationTest;
import com.app.budget.infrastructure.gateways.UserMapper;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import com.app.budget.infrastructure.persistence.repositories.UserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BoardControllerTest extends AbstractIntegrationTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository jdbcUserRepository;

    private String loggedUserToken;

    @BeforeEach
    void clearRepository(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(
                jdbcTemplate,
                "boards"
        );

        loggedUserToken = getUserToken();
    }

    @Test
    void shouldBeAbleToCreateBoard() {
        given().contentType(ContentType.JSON).
                header("Authorization", "Bearer " + loggedUserToken)
                .when()
                .post("/board")
                .then()
                .statusCode(201)
                .body(equalTo("Board created successfully!"));
    }

    private String getUserToken() {
        UserMapper userMapper = new UserMapper();
        User loggedUser = new User("Jack", "jack@bauer.com", "Pipoc@85", UserRole.USER);
        UserEntity loggedUserEntity = userMapper.toEntity(loggedUser, loggedUser.getPassword());

        jdbcUserRepository.save(loggedUserEntity);

        return tokenService.generateToken(loggedUserEntity);
    }

}
