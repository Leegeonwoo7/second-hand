package com.secondhandplatform.repository;

import com.secondhandplatform.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<Long, User> {
    
}
