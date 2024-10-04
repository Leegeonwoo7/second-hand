package com.secondhandplatform.config;

import com.secondhandplatform.common.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFilter authenticationFilter;

    //TODO 현재 모든 요청에대해서 authenticationFilter가 동작하는데 부분적으로 작동하도록 설정하기 옵시디언 필터 메모 참고
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/","/error", "/users/**", "/products", "/products/*").permitAll()
                .requestMatchers("/products/edit", "/products/", "/orders/**", "/orders").authenticated()
                // /product/edit, /products/new 와 같은 경로는 정적으로 추가해주어야함
                .anyRequest().authenticated()
        );

        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
