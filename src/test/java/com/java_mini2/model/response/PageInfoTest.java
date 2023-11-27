package com.java_mini2.model.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageInfoTest {

    @Test
    void pageInfoBuilderShouldCreateInstance() {
        PageInfo pageInfo = new PageInfo(true, false, 10L);

        assertNotNull(pageInfo);
        assertTrue(pageInfo.isHasNextPage());
        assertFalse(pageInfo.isHasPreviousPage());
        assertEquals(10L, pageInfo.getTotal());
    }
}
