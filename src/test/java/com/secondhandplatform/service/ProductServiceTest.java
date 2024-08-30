package com.secondhandplatform.service;

import com.secondhandplatform.domain.product.Category;
import com.secondhandplatform.domain.product.ProductStatus;
import com.secondhandplatform.domain.product.SellingStatus;
import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import com.secondhandplatform.dto.product.request.ProductSaveRequest;
import com.secondhandplatform.dto.product.response.ProductResponse;
import com.secondhandplatform.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @DisplayName("상품을 등록한다")
    void register() {
        // given
        User userA = createUser("userA");
        User savedUser = userRepository.save(userA);

        ProductSaveRequest request = createProductRequest("아이패드", "상태좋은 에어5세대", 50000);

        //when
        ProductResponse product = productService.register(request, userA.getLoginId());

        //then
        assertThat(product.getUserId()).isEqualTo(savedUser.getId());
        assertThat(product.getName()).isEqualTo("아이패드");
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

    private User createUser(String loginId) {
        LocalDate birthday = LocalDate.of(1992, 2, 15);

        return User.builder()
                .loginId(loginId)
                .password("1234")
                .email("email")
                .phone("01012341234")
                .birthday(birthday)
                .userType(UserType.USER)
                .signupType(SignupType.APP)
                .build();
    }
}