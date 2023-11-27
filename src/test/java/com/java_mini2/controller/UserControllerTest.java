package com.java_mini2.controller;

import com.java_mini2.exception.UserCustomException;
import com.java_mini2.model.entity.User;
import com.java_mini2.model.response.BackendResponse;
import com.java_mini2.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserShouldReturnOkResponse() throws UserCustomException {
        int size = 5;

        List<User> mockUsers = Arrays.asList(new User(), new User(), new User());
        when(userService.createUser(eq(size))).thenReturn(mockUsers);
        ResponseEntity<List<User>> responseEntity = userController.createUser(size);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUsers, responseEntity.getBody());
    }

    @Test
    void getUsersShouldReturnOkResponse() throws UserCustomException {
        String sortType = "name";
        String sortOrder = "even";
        int limit = 10;
        int offset = 0;

        BackendResponse mockResponse = new BackendResponse();
        when(userService.getUsers(eq(sortType), eq(sortOrder), eq(limit), eq(offset))).thenReturn(mockResponse);
        ResponseEntity<BackendResponse> responseEntity = userController.getUsers(sortType, sortOrder, limit, offset);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponse, responseEntity.getBody());
    }
}
