package com.java_mini2.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserConstantTest {

    @Test
    void testUserConstants() {
        assertEquals("VERIFIED", UserConstant.VERIFIED);
        assertEquals("TO_BE_VERIFIED", UserConstant.TO_BE_VERIFIED);
        assertEquals("Failed to fetch data from external API.", UserConstant.API_FAILURE);
        assertEquals("Something went wrong in getting users.", UserConstant.SOMETHING_WENT_WRONG);
        assertEquals("name", UserConstant.SORT_BY_NAME);
        assertEquals("age", UserConstant.SORT_BY_AGE);
        assertEquals("even", UserConstant.SORT_ORDER_EVEN);
        assertEquals("odd", UserConstant.SORT_ORDER_ODD);
        assertEquals("Invalid input. It should have sortType: name, age and sortOrder: even, odd.", UserConstant.INVALID_SORT_TYPE_ORDER);
        assertEquals("Invalid size limit. It should be between 1 and 5.", UserConstant.INVALID_LIMIT_VALUE);
        assertEquals("Invalid offset. It should be a non-negative value.", UserConstant.INVALID_OFFSET_VALUE);
    }
}
