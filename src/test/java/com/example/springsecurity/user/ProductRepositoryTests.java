package com.example.springsecurity.user;

import com.example.springsecurity.product.Product;
import com.example.springsecurity.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("product1",10,20);
    }

    @Test
    public void addProductTest() {
        //when
        Product savedProduct = productRepository.save(product);

        //then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isPositive();
    }

    @Test
    public void updateProductTest() {
        // given
        productRepository.save(product);
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        Product databaseProduct = optionalProduct.orElseThrow();

        // when
        databaseProduct.setName("updatedName");
        Product updatedUser = productRepository.save(product);

        // then
        assertThat(updatedUser.getName()).isEqualTo("updatedName");
    }

    @Test
    public void findAllProductsTest() {
        // given
        productRepository.save(product);

        // when
        Iterable<Product> products = productRepository.findAll();

        // then
        assertThat(products).hasSizeGreaterThan(0);
    }

    @Test
    public void findProductByIdTest() {
        // given
        productRepository.save(product);

        // when
        Optional<Product> productOptional = productRepository.findById(product.getId());

        // then
        assertThat(productOptional).isPresent();
    }

    @Test
    public void deleteProductByIdTest() {
        // given
        productRepository.save(product);
        Long productId = product.getId();

        // when
        productRepository.deleteById(productId);

        // then
        assertThat(productRepository.findById(productId)).isEmpty();
    }
}
