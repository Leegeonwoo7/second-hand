package com.secondhandplatform.common;

import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.provider.TokenProvider;
import com.secondhandplatform.user.domain.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("Start my filter ...");

        try {
            String token = parseBearerToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String userId = tokenProvider.validate(token);
            if (userId == null) {
                log.debug("tokenProvider.validate: {}", userId);
                filterChain.doFilter(request, response);
            }

            Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));

            User user;

            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            } else {
                log.warn("optionalUser가 null입니다.");
                throw new RuntimeException();
            }

            String role = user.getUserType()
                    .toString();
            log.debug("user.getRole.toString: {}", role);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getLoginId(), null, authorities);

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }



    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization) {
            log.warn("요청에 토큰이 존재하지 않습니다.");
            return null;
        }

        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) {
            log.warn("토큰이 존재하지만 Bearer토큰이 아닙니다.");
            return null;
        }

        return authorization.substring(7);
    }
}
