package com.secondhandplatform.common.filter;

import com.secondhandplatform.common.exception.BadRequestException;
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

import static com.secondhandplatform.common.exception.BadRequestException.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        boolean skipFilter = isSkipFilter(requestURI);

        if (skipFilter) {
            log.info("skip request URI: {}", requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        log.info("request URI: {}", requestURI);
        log.debug("Start my filter ...");

        try {
            String token = parseBearerToken(request);
            if (token == null) {
                log.info("토큰이 비어있습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            //토큰이 위조되었는지 확인하고 위조되지 않은 토큰일 경우 USER의 식별자 반환
            String userId = tokenProvider.validate(token);
            if (userId == null) {
                log.info("유효한 토큰이 아닙니다.");
                filterChain.doFilter(request, response);
            }

            User user = userRepository.findById(Long.valueOf(userId))
                    .orElseThrow(() -> new BadRequestException(DEFAULT_MESSAGE));

            String role = user.getUserType()
                    .toString();
            log.debug("user.getRole.toString: {}", role);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            // 수정 user.getUsername -> user.getId
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(), null, authorities);

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private boolean isSkipFilter(String requestURI) {
        if ("/products".equals(requestURI) ||
                "/users/login".equals(requestURI) ||
                "/users/join".equals(requestURI) ||
                "/users/id-check".equals(requestURI) ||
                "/users/email-check".equals(requestURI) ||
                "/users/email-certification".equals(requestURI) ||
                "/users/code-check".equals(requestURI)) {
            return true;
        }

        if (requestURI.matches("^/products/\\d+$") || requestURI.matches("^/products/user/\\d+$")) {
            return true;
        }

        return false;
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
