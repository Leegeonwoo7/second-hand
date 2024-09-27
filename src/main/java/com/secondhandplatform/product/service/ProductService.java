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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.secondhandplatform.common.exception.BadRequestException.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 상품 등록
    public ProductResponse registerProduct(RegisterProductRequest request, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(()
                -> new BadRequestException(LOGIN_FIRST));

        Product product = request.toEntity();
        product.registerBy(user);

        Product savedProduct = productRepository.save(product);
        return ProductResponse.of(savedProduct);
    }

    // 상품 수정
    @Transactional
    public ProductResponse editProduct(EditProductRequest request) {
        Long productId = request.getProductId();
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(NOT_EXIST_PRODUCT));

        Product editedProduct = findProduct.updateProduct(request);
        return ProductResponse.of(editedProduct);
    }

    // 상품 단건조회
    public ProductResponse findProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        Product product = optionalProduct.orElseThrow(()
                -> new BadRequestException(NOT_EXIST_PRODUCT));

        return ProductResponse.of(product);
    }

    // 회원의 모든상품조회
    public List<ProductResponse> findProductsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(NOT_EXIST_USER));

        List<Product> productList = productRepository.findByUser(user);

        return productList.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    // 메인화면 상품목록조회
    public List<ProductResponse> findProducts() {
        List<Product> productList = productRepository.findAll();

        return productList.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    // 상품삭제
    public void removeProduct(RemoveProductRequest request) {
        Long productId = request.getProductId();
        Long userId = request.getUserId();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(NOT_EXIST_PRODUCT));

        productRepository.delete(product);
    }




}
