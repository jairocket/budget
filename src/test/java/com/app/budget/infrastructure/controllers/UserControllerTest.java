package com.app.budget.infrastructure.controllers;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.core.services.TokenService;
import com.app.budget.infrastructure.AbstractIntegrationTest;
import com.app.budget.infrastructure.controllers.dto.UpdateNameDTO;
import com.app.budget.infrastructure.controllers.dto.UpdatePasswordDTO;
import com.app.budget.infrastructure.controllers.dto.UpdateRoleDTO;
import com.app.budget.infrastructure.controllers.dto.UserRegisterDTO;
import com.app.budget.infrastructure.gateways.UserMapper;
import com.app.budget.infrastructure.persistence.repositories.UserRepositoryImpl;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserControllerTest extends AbstractIntegrationTest {
    @Autowired
    private UserRepositoryImpl jdbcUserRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void clearRepository(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(
                jdbcTemplate,
                "users"
        );
    }

    @Test
    void shouldBeAbleToSaveNewUser() {
        String name = "Michael Jordan";
        String email = "mj@nba.com";
        String password = "Pipoc@85";

        UserRegisterDTO registerDTO = new UserRegisterDTO(name, email, password);

        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/save")
                .then()
                .statusCode(201).body(equalTo("Success!"));
    }

    @Test
    void shouldBeAbleToSaveAdminUserWithAdminCredentials() {
        var adminUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.ADMIN);
        var adminUserEntity = userMapper.toEntity(adminUser, adminUser.getPassword());
        var adminToken = tokenService.generateToken(adminUserEntity);
        jdbcUserRepository.save(adminUserEntity);

        String name = "Michael Jordan";
        String email = "mj@nba.com";
        String password = "Pipoc@85";
        UserRegisterDTO registerDTO = new UserRegisterDTO(name, email, password);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(registerDTO)
                .when()
                .post("users/save/admin")
                .then()
                .statusCode(201)
                .body(equalTo("Success!"));
    }

    @Test
    void shouldThrowErrorIfTryRegisterAUserThatAlreadyExists() {
        String name = "Oscar Schmidt";
        String email = "os@nba.com";
        String password = "Pipoc@85";
        var adminUser = new User(name, email, password, UserRole.ADMIN);
        var adminUserEntity = userMapper.toEntity(adminUser, adminUser.getPassword());
        jdbcUserRepository.save(adminUserEntity);

        UserRegisterDTO registerDTO = new UserRegisterDTO(adminUserEntity.getName(), adminUserEntity.getEmail(), adminUser.getPassword());

        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/save")
                .then()
                .statusCode(400)
                .body("message", is("User already exists"));
    }

    @Test
    void shouldNotBeAbleToSaveNewAdminUserIfNotAuthenticated() {
        String name = "Magic Johnson";
        String email = "magic@nba.com";
        String password = "Pipoc@85";

        UserRegisterDTO registerDTO = new UserRegisterDTO(name, email, password);

        given()
                .contentType(ContentType.JSON)
                .body(registerDTO)
                .when()
                .post("users/save/admin")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldNotBeAbleToRegisterNewSaveUserIfDoesNotHaveRoleAdmin() {
        var regularUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.USER);
        var regularUserEntity = userMapper.toEntity(regularUser, regularUser.getPassword());
        var regularUserToken = tokenService.generateToken(regularUserEntity);
        jdbcUserRepository.save(regularUserEntity);

        String name = "Michael Jordan";
        String email = "mj@nba.com";
        String password = "Pipoc@85";
        UserRegisterDTO registerDTO = new UserRegisterDTO(name, email, password);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer" + regularUserToken)
                .body(registerDTO)
                .when()
                .post("users/save/admin")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldBeAbleToFindAllUsers() {
        var adminUser = new User("Oscar Schmidt", "oscar2@nba.com", "Pipoc@85", UserRole.ADMIN);
        var adminUserEntity = userMapper.toEntity(adminUser, adminUser.getPassword());
        var adminToken = tokenService.generateToken(adminUserEntity);
        var regularUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.USER);
        var regularUserEntity = userMapper.toEntity(regularUser, regularUser.getPassword());
        jdbcUserRepository.save(regularUserEntity);
        jdbcUserRepository.save(adminUserEntity);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("name", hasItem("Oscar Schmidt"))
                .body("name", hasItem("Marcelinho"));
    }

    @Test
    void shouldNotBeAbleToFindAllUsersIfDoesNotHaveAdminRole() {
        var adminUser = new User("Oscar Schmidt", "oscar2@nba.com", "Pipoc@85", UserRole.ADMIN);
        var adminUserEntity = userMapper.toEntity(adminUser, adminUser.getPassword());
        var regularUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.USER);
        var regularUserEntity = userMapper.toEntity(regularUser, regularUser.getPassword());
        var regularToken = tokenService.generateToken(regularUserEntity);

        jdbcUserRepository.save(regularUserEntity);
        jdbcUserRepository.save(adminUserEntity);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + regularToken)
                .when()
                .get("/users")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldBeAbleToUpdatePassword() {
        var regularUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.USER);
        var regularUserEntity = userMapper.toEntity(regularUser, regularUser.getPassword());
        var regularToken = tokenService.generateToken(regularUserEntity);

        jdbcUserRepository.save(regularUserEntity);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + regularToken)
                .body(new UpdatePasswordDTO("Pipoc@86"))
                .when()
                .put("/users")
                .then()
                .statusCode(200);

    }

    @Test
    void shouldNotBeAbleToUpdateUserRoleIfDoesNotHaveAdminRole() {
        var adminUser = new User("Oscar Schmidt", "oscar2@nba.com", "Pipoc@85", UserRole.ADMIN);
        var adminUserEntity = userMapper.toEntity(adminUser, adminUser.getPassword());
        var regularUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.USER);
        var regularUserEntity = userMapper.toEntity(regularUser, regularUser.getPassword());
        var regularToken = tokenService.generateToken(regularUserEntity);

        jdbcUserRepository.save(adminUserEntity);
        var savedRegularUser = jdbcUserRepository.save(regularUserEntity);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + regularToken)
                .body(new UpdateRoleDTO("ADMIN"))
                .when()
                .put("/users/role/" + savedRegularUser)
                .then()
                .statusCode(403);
    }

    @Test
    void shouldBeAbleToToUpdateUserRoleIfHasAdminRole() {
        var adminUser = new User("Oscar Schmidt", "oscar2@nba.com", "Pipoc@85", UserRole.ADMIN);
        var adminUserEntity = userMapper.toEntity(adminUser, adminUser.getPassword());
        var adminToken = tokenService.generateToken(adminUserEntity);
        var regularUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.USER);
        var regularUserEntity = userMapper.toEntity(regularUser, regularUser.getPassword());
        jdbcUserRepository.save(adminUserEntity);
        var savedRegularUser = jdbcUserRepository.save(regularUserEntity);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .body(new UpdateRoleDTO("ADMIN"))
                .when()
                .put("/users/role/" + savedRegularUser)
                .then()
                .statusCode(200);
    }

    @Test
    void shouldBeAbleToUpdateUserName() {
        var regularUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.USER);
        var regularUserEntity = userMapper.toEntity(regularUser, regularUser.getPassword());
        var regularToken = tokenService.generateToken(regularUserEntity);

        jdbcUserRepository.save(regularUserEntity);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + regularToken)
                .body(new UpdateNameDTO("Lucas"))
                .when()
                .put("/users/name")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldBeAbleToFindUserByIdWithAdminCredentials() {
        var adminUser = new User("Oscar Schmidt", "oscar2@nba.com", "Pipoc@85", UserRole.ADMIN);
        var adminUserEntity = userMapper.toEntity(adminUser, adminUser.getPassword());
        var adminToken = tokenService.generateToken(adminUserEntity);
        var regularUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.USER);
        var regularUserEntity = userMapper.toEntity(regularUser, regularUser.getPassword());
        Long userId = jdbcUserRepository.save(regularUserEntity);
        jdbcUserRepository.save(adminUserEntity);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get("/users/get/" + userId.toString())
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", not(equalTo("Oscar Schmidt")))
                .body("name", equalTo("Marcelinho"))
                .body("role", equalTo("USER"));
    }

    @Test
    void shouldNotBeAbleToFindUserByIdWithoutAdminCredentials() {
        var adminUser = new User("Oscar Schmidt", "oscar2@nba.com", "Pipoc@85", UserRole.ADMIN);
        var adminUserEntity = userMapper.toEntity(adminUser, adminUser.getPassword());
        var regularUser = new User("Marcelinho", "marcelinho@br.com", "Pipoc@85", UserRole.USER);
        var regularUserEntity = userMapper.toEntity(regularUser, regularUser.getPassword());
        var regularUserToken = tokenService.generateToken(regularUserEntity);

        Long userId = jdbcUserRepository.save(regularUserEntity);
        jdbcUserRepository.save(adminUserEntity);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + regularUserToken)
                .when()
                .get("/users/get/" + userId.toString())
                .then()
                .statusCode(403);
    }

}
