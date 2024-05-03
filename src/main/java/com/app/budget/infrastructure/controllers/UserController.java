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

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        UserRole role = UserRole.USER;
        User user = userDTOMapper.toDomain(userRegisterDTO, role);
        User newUser = userService.register(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        UserRegisterResponseDTO userRegisterResponseDTO = userDTOMapper.toResponse(newUser);

        return ResponseEntity.created(uri).body(userRegisterResponseDTO);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<UserRegisterResponseDTO> registerAdmin(@RequestBody UserRegisterDTO userRegisterDTO) {
        UserRole role = UserRole.ADMIN;
        User user = userDTOMapper.toDomain(userRegisterDTO, role);
        User newUser = userService.register(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        UserRegisterResponseDTO userRegisterResponseDTO = userDTOMapper.toResponse(newUser);

        return ResponseEntity.created(uri).body(userRegisterResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserRegisterResponseDTO>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users.stream().map(userDTOMapper::toResponse).toList());
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
