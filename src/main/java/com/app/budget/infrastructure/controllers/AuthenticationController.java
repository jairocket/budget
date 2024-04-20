package com.app.budget.infrastructure.controllers;

import com.app.budget.core.domain.User;
import com.app.budget.core.services.TokenService;
import com.app.budget.core.services.UserService;
import com.app.budget.infrastructure.controllers.dto.AuthenticationDTO;
import com.app.budget.infrastructure.controllers.dto.UserRegisterDTO;
import com.app.budget.infrastructure.controllers.dto.UserRegisterResponseDTO;
import com.app.budget.infrastructure.gateways.UserDTOMapper;
import com.app.budget.infrastructure.persistence.entities.UserEntity;
import com.app.budget.infrastructure.persistence.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Value("${jwt.cookie-expiration}")
    private Integer MAX_AGE;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDTOMapper userDTOMapper;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(MAX_AGE)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        User user = userDTOMapper.toDomain(userRegisterDTO);
        User newUser = userService.register(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        UserRegisterResponseDTO userRegisterResponseDTO = userDTOMapper.toResponse(newUser);

        return ResponseEntity.created(uri).body(userRegisterResponseDTO);
    }
}
