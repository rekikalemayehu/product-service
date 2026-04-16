package com.ctbe.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctbe.productservice.model.product;

@Repository
public interface ProductRepository extends JpaRepository<product, Long> {
    List<product> findByNameContainingIgnoreCase(String keyword);
}
