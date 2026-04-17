package com.ctbe.productservice.service;

import com.ctbe.productservice.dto.ProductRequest;
import com.ctbe.productservice.dto.ProductResponse;
import com.ctbe.productservice.exception.ResourceNotFoundException;
import com.ctbe.productservice.model.Product;
import com.ctbe.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // Read - Get all products
    public List<ProductResponse> findAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // Read - Get product by ID
    public ProductResponse findById(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return toResponse(product);
    }

    // Create - Save new product
    public ProductResponse create(ProductRequest request) {
        Product product = toEntity(request);
        Product saved = repo.save(product);
        return toResponse(saved);
    }

    // Update - Replace existing product
    public ProductResponse update(Long id, ProductRequest request) {
        Product existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        
        existing.setName(request.getName());
        existing.setPrice(request.getPrice());
        existing.setStockQty(request.getStockQty());
        existing.setCategory(request.getCategory());
        
        Product updated = repo.save(existing);
        return toResponse(updated);
    }

    // Delete - Remove product
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        repo.deleteById(id);
    }

    // Mapping helpers
    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getStockQty(),
            product.getCategory()
        );
    }

    private Product toEntity(ProductRequest request) {
        return new Product(
            request.getName(),
            request.getPrice(),
            request.getStockQty(),
            request.getCategory()
        );
    }
}