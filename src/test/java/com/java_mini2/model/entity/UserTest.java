package com.java_mini2.model.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    void saveUserShouldReturnSavedUser() {
        Long userId = 1L;
        String name = "John Doe";
        String firstName = "John";
        int age = 25;
        String gender = "Male";
        String dob = "1998-01-01";
        String nationality = "US";
        String verificationStatus = "VERIFIED";
        LocalDateTime dateCreated = LocalDateTime.parse("2023-11-22T12:50:17.767216");
        LocalDateTime dateModified = LocalDateTime.parse("2023-11-22T12:50:17.767216");

        User user = new User(userId, name, firstName, age, gender, dob, nationality, verificationStatus, dateCreated, dateModified);

        assertNotNull(user);
        assertEquals(1L, user.getUserId());
        assertEquals("John Doe", user.getName());
        assertEquals("John", user.getFirstName());
        assertEquals(25, user.getAge());
        assertEquals("Male", user.getGender());
        assertEquals("1998-01-01", user.getDob());
        assertEquals("US", user.getNationality());
        assertEquals("VERIFIED", user.getVerificationStatus());
        assertEquals(LocalDateTime.parse("2023-11-22T12:50:17.767216"), user.getDateCreated());
        assertEquals(LocalDateTime.parse("2023-11-22T12:50:17.767216"), user.getDateModified());
    }
}
