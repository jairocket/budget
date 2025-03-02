package com.app.budget.infrastructure.controllers;

import com.app.budget.core.services.BoardService;
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

    @PostMapping
    public ResponseEntity<Long> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long createdBoardId = boardService.save(token);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/")
                .buildAndExpand(createdBoardId).toUri();

        return ResponseEntity.created(uri).build();
    }


}
