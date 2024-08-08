package com.secondhandplatform.domain.user;

import com.secondhandplatform.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id", unique = true, length = 15, nullable = false)
    private String loginId;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 15, unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(name = "store_name", length = 15, unique = true)
    private String name;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "signup_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SignupType signupType;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified;
}
