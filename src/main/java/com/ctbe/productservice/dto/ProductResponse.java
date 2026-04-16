package com.ctbe.productservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product response sent to clients")
public class ProductResponse {
    private Long id;
    private String name;
    private double price;
    private int stockQty;
    private String category;

    public ProductResponse(Long id, String name, double price, int stockQty, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQty = stockQty;
        this.category = category;
    }

    // Getters (no setters needed - response is read-only)
    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQty() { return stockQty; }
    public String getCategory() { return category; }
}
