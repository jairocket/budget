package com.app.budget.infrastructure.controllers;

import com.app.budget.core.services.BoardService;
import com.app.budget.core.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long loggedUserId = tokenService.getUserIdFromToken(token);
        Long createdBoardId = boardService.save(loggedUserId);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/")
                .buildAndExpand(createdBoardId).toUri();

        return ResponseEntity.created(uri).body("Board created successfully!");
    }


}
