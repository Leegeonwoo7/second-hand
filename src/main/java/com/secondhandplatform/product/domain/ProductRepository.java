package com.secondhandplatform.product.domain;

import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUser(User user);

}
