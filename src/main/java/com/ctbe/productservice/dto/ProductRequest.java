package com.ctbe.productservice.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for creating/updating a product")
public class ProductRequest {
    @NotBlank(message = "Name is required")
    @Schema(description = "Product name", example = "Laptop")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Schema(description = "Product price", example = "999.99")
    private Double price;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Schema(description = "Available stock", example = "10")
    private int stockQty;

    @NotBlank(message = "Category is required")
    @Schema(description = "Product category", example = "Electronics")
    private String category;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public int getStockQty() { return stockQty; }
    public void setStockQty(int stockQty) { this.stockQty = stockQty; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}