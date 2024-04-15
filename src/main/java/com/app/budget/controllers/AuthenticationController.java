package com.app.budget.controllers;

import com.app.budget.dto.AuthenticationDTO;
import com.app.budget.dto.LoginResponseDTO;
import com.app.budget.dto.UserRegisterDTO;
import com.app.budget.persistence.entities.UserEntity;
import com.app.budget.persistence.repositories.UserRepository;
import com.app.budget.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserRegisterDTO userRegisterDTO) {
        if (this.userRepository.findByEmail(userRegisterDTO.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = passwordEncoder.encode(userRegisterDTO.password());
        UserEntity entity = new UserEntity(userRegisterDTO.name(), userRegisterDTO.email(), encryptedPassword, userRegisterDTO.role());

        this.userRepository.save(entity);

        return ResponseEntity.ok().build();
    }
}
