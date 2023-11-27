package com.java_mini2.model.response;

import com.java_mini2.model.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BackendResponseTest {

    @Test
    void backendResponseBuilderShouldCreateInstance() {
        List<User> userData = Arrays.asList(
                new User(1L, "John Doe", "John", 25, "Male", "1998-01-01", "US", "VERIFIED", null, null),
                new User(2L, "Jane Doe", "Jane", 30, "Female", "1995-05-15", "UK", "TO_BE_VERIFIED", null, null)
        );

        PageInfo pageInfo = new PageInfo(true, false, 2L);
        BackendResponse backendResponse = BackendResponse.builder()
                .data(userData)
                .pageInfo(pageInfo)
                .build();

        assertNotNull(backendResponse);
        assertEquals(userData, backendResponse.getData());
        assertEquals(pageInfo, backendResponse.getPageInfo());
    }
}
