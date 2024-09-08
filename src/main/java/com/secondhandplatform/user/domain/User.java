package com.secondhandplatform.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
@ToString
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    //, nullable = false
    @Column(name = "username", unique = true, length = 15)
    private String username;

    //, nullable = false
    @Column(length = 255)
    private String password;

    //, nullable = false
    @Column(length = 50, unique = true)
    private String email;

    //, nullable = false
    @Column(length = 15, unique = true)
    private String phone;

    //nullable = false
    @Column()
    private LocalDate birthday;

    @Column(name = "store_name", length = 15, unique = true)
    private String name;

    //, nullable = false
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    //, nullable = false
    @Column(name = "signup_type")
    @Enumerated(EnumType.STRING)
    private SignupType signupType;

    //TODO 유저 - 찜 양방향 관계설정
    //@OneToMany
    //private List<Favorite> favorites;

    @Builder
    private User(String username, String password, String email, String phone, LocalDate birthday, String name, UserType userType, SignupType signupType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.name = name;
        this.userType = userType;
        this.signupType = signupType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        User user = (User) object;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPhone(), user.getPhone()) &&
                Objects.equals(getBirthday(), user.getBirthday()) &&
                Objects.equals(getName(), user.getName()) && getUserType() == user.getUserType() &&
                getSignupType() == user.getSignupType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getEmail(), getPhone(), getBirthday(), getName(), getUserType(), getSignupType());
    }
}
