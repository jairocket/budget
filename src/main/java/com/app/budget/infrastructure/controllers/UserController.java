package com.app.budget.infrastructure.controllers;

import com.app.budget.core.domain.User;
import com.app.budget.core.enums.UserRole;
import com.app.budget.core.services.UserService;
import com.app.budget.infrastructure.controllers.dto.*;
import com.app.budget.infrastructure.gateways.UserDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOMapper userDTOMapper;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody UserRegisterDTO userRegisterDTO) {
        User newUser = new User(
                userRegisterDTO.name(),
                userRegisterDTO.email(),
                userRegisterDTO.password(),
                UserRole.USER
        );
        Long savedNewUserId = userService.save(newUser);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(savedNewUserId).toUri();

        return ResponseEntity.created(uri).body("Success!");
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        User newUser = userService.register(userRegisterDTO.name(), userRegisterDTO.email(), userRegisterDTO.password(), UserRole.USER);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();
        UserRegisterResponseDTO userRegisterResponseDTO = userDTOMapper.toResponse(newUser);

        return ResponseEntity.created(uri).body(userRegisterResponseDTO);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<UserRegisterResponseDTO> registerAdmin(@RequestBody UserRegisterDTO userRegisterDTO) {
        User newUser = userService.register(userRegisterDTO.name(), userRegisterDTO.email(), userRegisterDTO.password(), UserRole.ADMIN);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();
        UserRegisterResponseDTO userRegisterResponseDTO = userDTOMapper.toResponse(newUser);

        return ResponseEntity.created(uri).body(userRegisterResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAll();
        List<UserResponseDTO> userResponseList = users.stream().map(user -> userDTOMapper.toUserDTO(user)).toList();
        return ResponseEntity.ok().body(userResponseList);
    }

    @PutMapping
    public ResponseEntity<UserRegisterResponseDTO> updatePassword(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody UpdatePasswordDTO updatePasswordDTO
    ) {
        userService.updatePassword(token, updatePasswordDTO.password());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/name")
    public ResponseEntity<UserRegisterResponseDTO> updateName(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody UpdateNameDTO updateNameDTO
    ) {
        User user = userService.updateName(token, updateNameDTO.name());
        UserRegisterResponseDTO userRegisterResponseDTO = userDTOMapper.toResponse(user);

        return ResponseEntity.ok().body(userRegisterResponseDTO);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<UserRegisterResponseDTO> updateRole(
            @PathVariable Long id,
            @RequestBody UpdateRoleDTO updateRoleDTO
    ) {
        User user = userService.updateRole(id, updateRoleDTO.role());
        UserRegisterResponseDTO userRegisterResponseDTO = userDTOMapper.toResponse(user);

        return ResponseEntity.ok().body(userRegisterResponseDTO);
    }
}
