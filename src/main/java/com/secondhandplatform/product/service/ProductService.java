package com.secondhandplatform.product.service;

import com.secondhandplatform.common.exception.BadRequestException;
import com.secondhandplatform.product.domain.Product;
import com.secondhandplatform.product.domain.ProductRepository;
import com.secondhandplatform.product.dto.request.EditProductRequest;
import com.secondhandplatform.product.dto.request.RegisterProductRequest;
import com.secondhandplatform.product.dto.request.RemoveProductRequest;
import com.secondhandplatform.product.dto.response.ProductResponse;
import com.secondhandplatform.user.domain.User;
import com.secondhandplatform.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.secondhandplatform.common.exception.BadRequestException.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 상품 등록
    public ProductResponse registerProduct(RegisterProductRequest request) {
        Long userId = request.getUserId();

        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(()
                -> new BadRequestException(LOGIN_FIRST));

        Product product = request.toEntity();
        product.registerBy(user);

        Product savedProduct = productRepository.save(product);
        return ProductResponse.of(savedProduct);
    }

    // 상품 수정
    public ProductResponse editProduct(EditProductRequest request) {
        Long productId = request.getProductId();
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(NOT_EXIST_PRODUCT));

        Product editedProduct = findProduct.updateProduct(request);
        return ProductResponse.of(editedProduct);
    }

    public void removeProduct(RemoveProductRequest request) {

    }


}
