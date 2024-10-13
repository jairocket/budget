package com.app.budget.infrastructure.controllers;

import com.app.budget.core.enums.UserRole;
import com.app.budget.infrastructure.AbstractIntegrationTest;
import com.app.budget.infrastructure.controllers.dto.AuthenticationDTO;
import com.app.budget.infrastructure.controllers.dto.UserRegisterDTO;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthenticationControllerTest extends AbstractIntegrationTest {
    @AfterEach
    void clearRepository(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(
                jdbcTemplate,
                "users"
        );
    }

    @Test
    void shouldBeAbleToLogin() {
        UserEntity entity = new UserEntity("Michael Jordan", "mj@nba.com", "Pipoc@85", UserRole.USER);
        UserRegisterDTO registerDTO = new UserRegisterDTO(entity.getName(), entity.getEmail(), entity.getPassword());

        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/save")
                .then()
                .statusCode(201)
                .body(equalTo("Success!"));

        AuthenticationDTO authenticationDTO = new AuthenticationDTO(entity.getEmail(), entity.getPassword());

        given()
                .contentType(ContentType.JSON)
                .body(authenticationDTO)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .cookie("token", notNullValue());

    }

    @Test
    void shouldNotBeAbleToLoginWithWrongPassword() {
        UserEntity entity = new UserEntity("Larry Byrd", "lb@nba.com", "Pipoc@85", UserRole.USER);
        UserRegisterDTO registerDTO = new UserRegisterDTO(entity.getName(), entity.getEmail(), entity.getPassword());

        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/save")
                .then()
                .statusCode(201)
                .body(equalTo("Success!"));

        AuthenticationDTO authenticationDTO = new AuthenticationDTO("lb@nba.com", "wrongPassword");

        given()
                .contentType(ContentType.JSON)
                .body(authenticationDTO)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(403);
    }
}
