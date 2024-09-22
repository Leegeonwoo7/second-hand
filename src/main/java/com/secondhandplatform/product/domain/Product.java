package com.secondhandplatform.product.domain;

import com.secondhandplatform.product.dto.request.EditProductRequest;
import com.secondhandplatform.user.domain.BaseEntity;
import com.secondhandplatform.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;


@Entity
@Getter
@NoArgsConstructor
@ToString
public class Product extends BaseEntity {

    @Column(name = "product_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //, nullable = false
    @Column(name = "product_name", length = 50)
    private String name;

    //nullable = false
    @Lob
    @Column()
    private String description;

    //nullable = false
    @Column()
    private int price;

    //nullable = false
//    @Column()
//    private int quantity;

    //nullable = false
    @Column()
    @Enumerated(EnumType.STRING)
    private Category category;

    //, nullable = false
    @Column(name = "product_status")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    //, nullable = false
    @Column(name = "selling_status")
    @Enumerated(EnumType.STRING)
    private SellingStatus sellingStatus;

    @Builder
    private Product(String name, String description, int price, int quantity, Category category, ProductStatus productStatus, SellingStatus sellingStatus) {
        this.name = name;
        this.description = description;
        this.price = price;
//        this.quantity = quantity;
        this.category = category;
        this.productStatus = productStatus;
        this.sellingStatus = sellingStatus;
    }

    //연관관계 편의 메서드
    //TODO 양방향 매핑시 메서드 수정필요
    public void registerBy(User user) {
        this.user = user;
    }

    public void changeSellingStatus() {
        this.sellingStatus = SellingStatus.RESERVED;
    }

    public Product updateProduct(EditProductRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.price = request.getPrice();
//        this.quantity = request.getQuantity();
        this.category = request.getCategory();
        this.productStatus = request.getProductStatus();
        this.sellingStatus = request.getSellingStatus();

        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Product product = (Product) object;
        return getPrice() == product.getPrice() && Objects.equals(getId(), product.getId()) &&
                Objects.equals(getUser(), product.getUser()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getDescription(), product.getDescription()) &&
                getCategory() == product.getCategory() &&
                getProductStatus() == product.getProductStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getName(), getDescription(), getPrice(), getCategory(), getProductStatus());
    }
}
