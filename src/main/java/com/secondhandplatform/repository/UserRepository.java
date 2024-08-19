package com.secondhandplatform.repository;

import com.secondhandplatform.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //이메일 중복체크
    boolean existsByEmail(String email);

    // 아이디 중복체크
    boolean existsByLoginId(String loginId);

    // 로그인 아이디 회원조회
    User findByLoginId(String loginId);
}
