package com.ctbe.productservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Column(nullable = false)
    private double price;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private int stockQty;

    @NotBlank(message = "Category is required")
    private String category;

    // Constructors
    public Product() {}

    public Product(String name, double price, int stockQty, String category) {
        this.name = name;
        this.price = price;
        this.stockQty = stockQty;
        this.category = category;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQty() { return stockQty; }
    public String getCategory() { return category; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStockQty(int stockQty) { this.stockQty = stockQty; }
    public void setCategory(String category) { this.category = category; }
}