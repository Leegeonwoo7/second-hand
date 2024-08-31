package com.secondhandplatform.service;

import com.secondhandplatform.domain.product.Category;
import com.secondhandplatform.domain.product.Product;
import com.secondhandplatform.domain.product.ProductStatus;
import com.secondhandplatform.domain.product.SellingStatus;
import com.secondhandplatform.domain.user.SignupType;
import com.secondhandplatform.domain.user.User;
import com.secondhandplatform.domain.user.UserType;
import com.secondhandplatform.dto.product.request.ProductSaveRequest;
import com.secondhandplatform.dto.product.response.ProductResponse;
import com.secondhandplatform.repository.ProductRepository;
import com.secondhandplatform.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void tearDown() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("상품을 등록한다")
    void register() {
        // given
        User userA = createUser("userA");
        ProductSaveRequest request = createProductRequest("아이패드", "아이패드 에어 5세대", 5000);

        Product product = request.toEntity(request);
        ReflectionTestUtils.setField(product, "id", 1L);

        when(userRepository.findByLoginId(userA.getLoginId())).thenReturn(userA);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.register(request, userA.getLoginId());

        assertThat(response.getId()).isEqualTo(product.getId());
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

        User userA = mock(User.class);
        when(userA.getId()).thenReturn(1L);
        when(userA.getName()).thenReturn("");
        when(userA.getLoginId()).thenReturn(loginId);
        when(userA.getUserType()).thenReturn(UserType.USER);
        when(userA.getSignupType()).thenReturn(SignupType.APP);
        when(userA.getBirthday()).thenReturn(birthday);
        when(userA.getEmail()).thenReturn("test@example.com");
        when(userA.getPhone()).thenReturn("01012341234");
        when(userA.getPassword()).thenReturn("1234");
        return userA;
    }
}