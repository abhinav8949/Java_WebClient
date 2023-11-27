package com.java_mini2.controller;

import com.java_mini2.exception.UserCustomException;
import com.java_mini2.model.entity.User;
import com.java_mini2.model.response.BackendResponse;
import com.java_mini2.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<List<User>> createUser(@RequestParam("size") int size) throws UserCustomException {
        return new ResponseEntity<>(userService.createUser(size), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BackendResponse> getUsers(
            @RequestParam("sortType") String sortType,
            @RequestParam("sortOrder") String sortOrder,
            @RequestParam("limit") int limit,
            @RequestParam("offset") int offset) throws UserCustomException {
        BackendResponse backendResponse = userService.getUsers(sortType, sortOrder, limit, offset);
        return new ResponseEntity<>(backendResponse, HttpStatus.OK);
    }
}

