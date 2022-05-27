package com.example.springsecurity.user;

import com.example.springsecurity.product.NoProductFoundException;
import com.example.springsecurity.product.Product;
import com.example.springsecurity.product.ProductRepository;
import com.example.springsecurity.product.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void findAllProductsTest() {
        // given
        Product product1 = new Product("product1",10,20);
        Product product2 = new Product("product2",1,2);
        List<Product> productList = List.of(product1, product2);
        when(productRepository.findAll()).thenReturn(productList);

        // when
        List<Product> actualProducts = productService.findAllProducts();

        // then
        assertThat(actualProducts).hasSize(2);
        assertThat(productList).isEqualTo(actualProducts);
    }

    @Test
    void addProductTest() {
        // given
        Product productToBeSaved = new Product("product1",10,20);
        when(productRepository.save(productToBeSaved)).thenReturn(productToBeSaved);

        // when
        Product actualProduct = productService.addProduct(productToBeSaved);

        // then
        verify(productRepository).save(productToBeSaved);
        assertThat(productToBeSaved).isEqualTo(actualProduct);
    }

    @Test
    void getProductByIdTest() throws NoProductFoundException {
        // given
        Product productToBeFound = new Product("product1",10,20);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productToBeFound));

        // when
        Product actualProduct = productService.getProductById(anyLong());

        // then
        assertThat(actualProduct).isEqualTo(productToBeFound);
    }

    @Test
    void getProductById_ExceptionTest() {
        // given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(NoProductFoundException.class,
                () -> productService.getProductById(anyLong()));
    }

    @Test
    void deleteProductByIdTest() throws NoProductFoundException {
        Product productToBeFound = new Product("product1",10,20);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productToBeFound));

        productService.deleteProductById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProductById_ExceptionTest() {
        // given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(NoProductFoundException.class,
                () -> productService.deleteProductById(anyLong()));
    }
}