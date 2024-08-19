package com.secondhandplatform.domain.user;

import com.secondhandplatform.domain.BaseEntity;
import com.secondhandplatform.domain.favorite.Favorite;
import com.secondhandplatform.domain.follow.Follow;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
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

//    @Column(name = "email_verified", nullable = false)
//    private Boolean emailVerified;

    //TODO 유저 - 찜 양방향 관계설정
    //@OneToMany
    //private List<Favorite> favorites;
//    , Boolean emailVerified
    @Builder
    private User(String loginId, String password, String email, String phone, LocalDate birthday, String name, UserType userType, SignupType signupType) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.name = name;
        this.userType = userType;
        this.signupType = signupType;
//        this.emailVerified = emailVerified;
    }
}
