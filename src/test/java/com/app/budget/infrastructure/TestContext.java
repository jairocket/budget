package com.app.budget.infrastructure;

import com.app.budget.core.services.TokenService;
import com.app.budget.infrastructure.persistence.repositories.UserRepository;
import com.app.budget.infrastructure.persistence.repositories.UserRepositoryImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestContext {
    static class LoginTestConfiguration {
        @Bean
        public UserRepository jdbcUserRepository() {
            return new UserRepositoryImpl();
        }

        @Bean
        public TokenService tokenService() {
            return new TokenService();
        }
    }

}
