package com.app.budget.infrastructure.controllers;

import com.app.budget.core.enums.UserRole;
import com.app.budget.infrastructure.AbstractIntegrationTest;
import com.app.budget.infrastructure.controllers.dto.UserRegisterDTO;
import com.app.budget.infrastructure.persistence.repositories.UserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserControllerTest extends AbstractIntegrationTest {
    @Autowired
    private UserRepository repository;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldBeAbleToRegisterNewUser() {
        String name = "Michael Jordan";
        String email = "mj@nba.com";
        String password = "Pipoc@85";
        UserRole role = UserRole.USER;

        UserRegisterDTO registerDTO = new UserRegisterDTO(name, email, password, role);

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
    }

    @Test
    void shouldThrowErrorIfTryRegisterAUserThatAlreadyExists() {
        String name = "Michael Jordan";
        String email = "mj@nba.com";
        String password = "Pipoc@85";
        UserRole role = UserRole.USER;

        UserRegisterDTO registerDTO = new UserRegisterDTO(name, email, password, role);

        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/register")
                .then()
                .statusCode(201);


        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/register")
                .then()
                .statusCode(400)
                .body("message", is("User already exists"))
        ;
    }
}
