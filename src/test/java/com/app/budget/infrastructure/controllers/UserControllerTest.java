package com.app.budget.infrastructure.controllers;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.core.services.TokenService;
import com.app.budget.infrastructure.AbstractIntegrationTest;
import com.app.budget.infrastructure.controllers.dto.UserRegisterDTO;
import com.app.budget.infrastructure.gateways.UserMapper;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void setUpUsers() {
        repository.deleteAll();
    }

    @Test
    void shouldBeAbleToRegisterNewUser() {
        String name = "Michael Jordan";
        String email = "mj@nba.com";
        String password = "Pipoc@85";

        UserRegisterDTO registerDTO = new UserRegisterDTO(name, email, password);

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
    void shouldBeAbleToRegisterAdminUser() {
        var adminUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.ADMIN);
        var adminUserEntity = userMapper.toEntity(adminUser.getId(), adminUser.getName(), adminUser.getPassword(), adminUser.getPassword(), adminUser.getRole());
        var adminToken = tokenService.generateToken(adminUserEntity);

        repository.save(adminUserEntity);

        String name = "Michael Jordan";
        String email = "mj@nba.com";
        String password = "Pipoc@85";

        UserRegisterDTO registerDTO = new UserRegisterDTO(name, email, password);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer" + adminToken)
                .body(registerDTO)
                .when()
                .post("users/register/admin")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", is("Michael Jordan"))
                .body("email", is("mj@nba.com"))
                .body("role", is("ADMIN"));
    }

    @Test
    void shouldThrowErrorIfTryRegisterAUserThatAlreadyExists() {
        String name = "Oscar Schmidt";
        String email = "os@nba.com";
        String password = "Pipoc@85";

        var adminUser = new User(name, email, password, UserRole.ADMIN);
        var adminUserEntity = userMapper.toEntity(adminUser.getId(), adminUser.getName(), adminUser.getPassword(), adminUser.getPassword(), adminUser.getRole());

        repository.save(adminUserEntity);

        UserRegisterDTO registerDTO = new UserRegisterDTO(adminUserEntity.getName(), adminUserEntity.getEmail(), adminUser.getPassword());


        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/register")
                .then()
                .statusCode(400)
                .body("message", is("User already exists"));
    }

    @Test
    void shouldNotBeAbleToRegisterNewAdminUserIfNotAuthenticated() {
        String name = "Magic Johnson";
        String email = "magic@nba.com";
        String password = "Pipoc@85";

        UserRegisterDTO registerDTO = new UserRegisterDTO(name, email, password);

        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/register/admin")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldNotBeAbleToRegisterNewAdminUserIfDoesNotHaveRoleAdmin() {
        var regularUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.USER);
        var regularUserEntity = userMapper.toEntity(regularUser.getId(), regularUser.getName(), regularUser.getPassword(), regularUser.getPassword(), regularUser.getRole());
        var regularUserToken = tokenService.generateToken(regularUserEntity);

        repository.save(regularUserEntity);

        String name = "Michael Jordan";
        String email = "mj@nba.com";
        String password = "Pipoc@85";


        UserRegisterDTO registerDTO = new UserRegisterDTO(name, email, password);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer" + regularUserToken)
                .body(registerDTO)
                .when()
                .post("users/register/admin")
                .then()
                .statusCode(403);
    }
}
