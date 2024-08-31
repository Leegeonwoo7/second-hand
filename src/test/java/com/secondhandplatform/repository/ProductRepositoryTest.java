package com.secondhandplatform.repository;

import com.secondhandplatform.domain.product.Category;
import com.secondhandplatform.domain.product.Product;
import com.secondhandplatform.domain.product.ProductStatus;
import com.secondhandplatform.domain.product.SellingStatus;
import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import com.secondhandplatform.dto.product.request.ProductSaveRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @DisplayName("회원이 등록한 모든 상품을 조회한다.")
    void findByUserId() {
        // given
        User userA = createUser("userA");

        User saveUser = userRepository.save(userA);
        Long userId = saveUser.getId();

        Product product1 = createProduct("목걸이");
        Product product2 = createProduct("반지");
        product1.registerBy(saveUser);
        product2.registerBy(saveUser);
        productRepository.save(product1);
        productRepository.save(product2);

        //when
        List<Product> byUserId = productRepository.findByUserId(userId);

        //then
        assertThat(byUserId).hasSize(2);
        assertThat(byUserId)
                .extracting("name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("목걸이", SellingStatus.SELLING),
                        tuple("반지", SellingStatus.SELLING)
                );

    }

    private static User createUser(String loginId) {
        LocalDate date = LocalDate.of(1998, 12, 5);

        User userA = User.builder()
                .loginId(loginId)
                .email("test@example.com")
                .phone("01012341234")
                .birthday(date)
                .userType(UserType.USER)
                .signupType(SignupType.APP)
                .password("1234")
                .build();
        return userA;
    }

    private Product createProduct(String productName) {
        return Product.builder()
                .name(productName)
                .category(Category.ACCSESSORIES)
                .description("거의 사용안함")
                .quantity(1)
                .productStatus(ProductStatus.GOOD)
                .price(10000)
                .sellingStatus(SellingStatus.SELLING)
                .build();
    }

    private ProductSaveRequest createProductRequest(String name, String description, int price) {
        return ProductSaveRequest.builder()
                .name(name)
                .description(description)
                .price(price)
                .productStatus(ProductStatus.GOOD)
                .sellingStatus(SellingStatus.SELLING)
                .quantity(1)
                .category(Category.ACCSESSORIES)
                .build();
    }
}