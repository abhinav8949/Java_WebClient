package com.java_mini2.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long userId;
    private String name;
    @Transient
    @JsonIgnore
    private String firstName;
    private int age;
    private String gender;
    private String dob;
    private String nationality;
    private String verificationStatus;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
}
