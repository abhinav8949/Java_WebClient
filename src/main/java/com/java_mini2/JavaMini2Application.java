package com.java_mini2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.java_mini2")
public class JavaMini2Application {

    public static void main(String[] args) {
        SpringApplication.run(JavaMini2Application.class, args);
    }

}

