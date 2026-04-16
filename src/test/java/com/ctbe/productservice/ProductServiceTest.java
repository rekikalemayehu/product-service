package com.ctbe.productservice;

import com.ctbe.productservice.dto.ProductResponse;
import com.ctbe.productservice.model.product;
import com.ctbe.productservice.repository.ProductRepository;
import com.ctbe.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void findById_returnsProductResponse_whenProductExists() {
        // Arrange - create a product entity (what the repository returns)
        product laptop = new product("Laptop", 1200.0, 10, "Electronics");
        laptop.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(laptop));

        // Act - call the service method (returns ProductResponse)
        ProductResponse result = productService.findById(1L);

        // Assert - verify the response DTO
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Laptop");
        assertThat(result.getPrice()).isEqualTo(1200.0);
        assertThat(result.getStockQty()).isEqualTo(10);
        assertThat(result.getCategory()).isEqualTo("Electronics");
    }

    @Test
    void findById_throwsException_whenProductNotFound() {
        // Arrange
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert - verify that exception is thrown
        org.junit.jupiter.api.Assertions.assertThrows(
            com.ctbe.productservice.exception.ResourceNotFoundException.class,
            () -> productService.findById(99L)
        );
    }
}