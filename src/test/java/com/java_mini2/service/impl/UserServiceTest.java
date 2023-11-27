package com.java_mini2.service.impl;

import com.java_mini2.exception.UserCustomException;
import com.java_mini2.model.entity.User;
import com.java_mini2.model.response.BackendResponse;
import com.java_mini2.model.response.PageInfo;
import com.java_mini2.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private WebClient mockApi1WebClient;
    @Mock
    private WebClient mockApi2WebClient;
    @Mock
    private WebClient mockApi3WebClient;
    @Mock
    private UserRepository mockUserRepository;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockApi1WebClient, mockApi2WebClient, mockApi3WebClient,
                mockUserRepository);
    }

    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setName("Jane Doe");
        user.setFirstName("Jane");
        user.setAge(43);
        user.setGender("male");
        user.setDob("27-12-1990");
        user.setNationality("US");
        user.setVerificationStatus("VERIFIED");
        user.setDateCreated(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        user.setDateModified(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        doReturn(null).when(mockApi1WebClient).get();

        assertThrows(UserCustomException.class, () -> userServiceUnderTest.createUser(5));
    }

    @Test
    void testGetUsers() throws Exception {
        BackendResponse expectedResult = BackendResponse.builder()
                .data(List.of(new User()))
                .pageInfo(PageInfo.builder()
                        .hasNextPage(false)
                        .hasPreviousPage(false)
                        .total(0L)
                        .build())
                .build();
        User user = new User();
        user.setName("name");
        user.setFirstName("firstName");
        user.setAge(0);
        user.setGender("gender");
        user.setDob("dob");
        user.setNationality("nationality");
        user.setVerificationStatus("verificationStatus");
        user.setDateCreated(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        user.setDateModified(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        Page<User> users = new PageImpl<>(List.of(user));
        assertThrows(UserCustomException.class, () -> userServiceUnderTest.getUsers("even", "odd", 3, 0));
    }

    @Test
    void testGetUsers_UserRepositoryFindAllByNameOrderReturnsNoItems() throws Exception {
        BackendResponse expectedResult = BackendResponse.builder()
                .data(List.of(new User()))
                .pageInfo(PageInfo.builder()
                        .hasNextPage(false)
                        .hasPreviousPage(false)
                        .total(0L)
                        .build())
                .build();
        when(mockUserRepository.findAllByNameOrder(eq(0), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        BackendResponse result = userServiceUnderTest.getUsers("name", "even", 2, 0);
        assertNotEquals(result, expectedResult);
    }

    @Test
    void testGetUsers_UserRepositoryFindAllByAgeOrderReturnsNoItems() throws Exception {
        BackendResponse expectedResult = BackendResponse.builder()
                .data(List.of(new User()))
                .pageInfo(PageInfo.builder()
                        .hasNextPage(false)
                        .hasPreviousPage(false)
                        .total(0L)
                        .build())
                .build();
        when(mockUserRepository.findAllByAgeOrder(eq(0), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        BackendResponse result = userServiceUnderTest.getUsers("age", "even", 2, 0);
        assertNotEquals(result, expectedResult);
    }
}
