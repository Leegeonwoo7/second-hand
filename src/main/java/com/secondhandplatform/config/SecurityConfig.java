//package com.secondhandplatform.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        // 접근 권한 URL
//        http.authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/").permitAll()
//                .requestMatchers("/", "/users/login", "/users/sign-up").permitAll()
//                .requestMatchers("/users/**").hasAnyRole("ADMIN", "USER")
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//        );
//
//        // 로그인 폼
//        http.formLogin((auth) -> auth
//                .loginPage("/users/login").permitAll()
//                .failureUrl("/users/login-error")
//        );
//
//        // TODO CSRF
//        http.csrf((auth) -> auth.disable());
//        return http.build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
