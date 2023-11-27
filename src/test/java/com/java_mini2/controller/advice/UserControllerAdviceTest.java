package com.java_mini2.controller.advice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserControllerAdviceTest {

    @Mock
    private Exception mockException;

    @InjectMocks
    private UserControllerAdvice userControllerAdvice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleExceptionShouldReturnErrorResponse() {
        when(mockException.getMessage()).thenReturn("Test error message");
        ResponseEntity<Map<String, Object>> responseEntity = userControllerAdvice.handleException(mockException);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
