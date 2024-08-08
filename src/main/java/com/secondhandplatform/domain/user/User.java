package com.secondhandplatform.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true, length = 15)
    private String name;

    @Column(length = 255)
    private String password;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 15, unique = true)
    private String phone;
    private LocalDate birthday;
}
