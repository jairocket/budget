package com.app.budget.infrastructure.persistence.repositories;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    UserDetails findByEmail(String email);

}
