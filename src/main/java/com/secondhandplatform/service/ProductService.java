package com.secondhandplatform.service;

import com.secondhandplatform.domain.product.Product;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.dto.product.request.ProductSaveRequest;
import com.secondhandplatform.dto.product.response.ProductResponse;
import com.secondhandplatform.repository.ProductRepository;
import com.secondhandplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    //상품 등록
    // 컨트롤러에서 토큰을 해석하고 해석한 토큰으로부터 username을 서비스계층으로 보낸다.
    // 서비스계층에서는 해당 uesrname으로 회원을 조회하고 연관관계를 설정한뒤 상품을 등록한다.
    public ProductResponse register(ProductSaveRequest request, String loginId) {
        Product product = request.toEntity(request);

        Product savedProduct = productRepository.save(product);
        User findUser = userRepository.findByLoginId(loginId);
        savedProduct.registerBy(findUser);

        return ProductResponse.of(savedProduct);
    }

    public ProductResponse findProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            log.info("findProduct - 존재하지 않는 productId");
            return null;
        }

        return ProductResponse.of(product.get());
    }
}
