package com.app.budget.infrastructure.controllers;

import com.app.budget.core.enums.UserRole;
import com.app.budget.infrastructure.AbstractIntegrationTest;
import com.app.budget.infrastructure.controllers.dto.AuthenticationDTO;
import com.app.budget.infrastructure.controllers.dto.UserRegisterDTO;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import com.app.budget.infrastructure.persistence.repositories.UserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AuthenticationControllerTest extends AbstractIntegrationTest {
    @Autowired
    private UserRepository repository;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldBeAbleToLogin() {
        UserEntity entity = new UserEntity("Michael Jordan", "mj@nba.com", "Pipoc@85", UserRole.USER);

        UserRegisterDTO registerDTO = new UserRegisterDTO(entity.getName(), entity.getEmail(), entity.getPassword());

        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/register")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", is("Michael Jordan"))
                .body("email", is("mj@nba.com"))
                .body("role", is("USER"));


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
        UserEntity entity = new UserEntity("Michael Jordan", "mj@nba.com", "Pipoc@85", UserRole.USER);

        UserRegisterDTO registerDTO = new UserRegisterDTO(entity.getName(), entity.getEmail(), entity.getPassword());

        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/register")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", is("Michael Jordan"))
                .body("email", is("mj@nba.com"))
                .body("role", is("USER"));


        AuthenticationDTO authenticationDTO = new AuthenticationDTO(entity.getEmail(), "wrongPassword");

        given()
                .contentType(ContentType.JSON)
                .body(authenticationDTO)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(403);

    }
}
