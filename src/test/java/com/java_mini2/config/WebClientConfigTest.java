package com.java_mini2.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "myapp.api1Url=http://demo.com/api1",
        "myapp.api2Url=http://demo.com/api2",
        "myapp.api3Url=http://demo.com/api3"
})
class WebClientConfigTest {

    @Autowired
    private WebClient api1WebClient;

    @Autowired
    private WebClient api2WebClient;

    @Autowired
    private WebClient api3WebClient;

    @Test
    void api1WebClientShouldNotBeNull() {
        assertNotNull(api1WebClient);
    }

    @Test
    void api2WebClientShouldNotBeNull() {
        assertNotNull(api2WebClient);
    }

    @Test
    void api3WebClientShouldNotBeNull() {
        assertNotNull(api3WebClient);
    }
}
