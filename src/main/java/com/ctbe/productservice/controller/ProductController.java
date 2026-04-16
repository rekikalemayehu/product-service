package com.ctbe.productservice;

import com.ctbe.productservice.model.product;
import com.ctbe.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repo;

    @Autowired
    private ObjectMapper mapper;

    private Long savedId;

    @BeforeEach
    void setUp() {
        product p = repo.save(new product("Test Laptop", 999.0, 10, "Electronics"));
        savedId = p.getId();
    }

    // Test GET /api/v1/products - 200 OK
    @Test
    void getAll_returns200() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    // Test GET /api/v1/products/{id} - 200 OK when exists
    @Test
    void getById_returns200_whenExists() throws Exception {
        mockMvc.perform(get("/api/v1/products/" + savedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Laptop")))
                .andExpect(jsonPath("$.category", is("Electronics")))
                .andExpect(jsonPath("$.price", is(999.0)))
                .andExpect(jsonPath("$.stockQty", is(10)));
    }

    // Test POST /api/v1/products - 201 Created
    @Test
    void create_returns201_withLocation() throws Exception {
        String body = """
                {
                    "name": "Headphones",
                    "price": 89.99,
                    "stockQty": 50,
                    "category": "Audio"
                }
                """;

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Headphones")))
                .andExpect(jsonPath("$.price", is(89.99)))
                .andExpect(jsonPath("$.stockQty", is(50)))
                .andExpect(jsonPath("$.category", is("Audio")));
    }

    // Test PUT /api/v1/products/{id} - 200 OK
    @Test
    void update_returns200() throws Exception {
        String body = """
                {
                    "name": "Pro Laptop",
                    "price": 1299.0,
                    "stockQty": 5,
                    "category": "Electronics"
                }
                """;

        mockMvc.perform(put("/api/v1/products/" + savedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Pro Laptop")))
                .andExpect(jsonPath("$.price", is(1299.0)))
                .andExpect(jsonPath("$.stockQty", is(5)));
    }

    // Test DELETE /api/v1/products/{id} - 204 No Content
    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/api/v1/products/" + savedId))
                .andExpect(status().isNoContent());
    }

    // Test GET /api/v1/products/{id} - 404 Not Found
    @Test
    void getById_returns404_whenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/products/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", containsString("9999")));
    }

    // Test DELETE /api/v1/products/{id} - 404 Not Found
    @Test
    void delete_returns404_whenNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/products/9999"))
                .andExpect(status().isNotFound());
    }

    // Test POST - 400 Bad Request when name is blank
    @Test
    void create_returns400_whenNameBlank() throws Exception {
        String invalid = """
                {
                    "name": "",
                    "price": 10.0,
                    "stockQty": 1,
                    "category": "Tech"
                }
                """;

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalid))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", containsString("Name is required")));
    }

    // Test POST - 400 Bad Request when price is negative
    @Test
    void create_returns400_whenPriceInvalid() throws Exception {
        String invalid = """
                {
                    "name": "Widget",
                    "price": -1,
                    "stockQty": 1,
                    "category": "Tech"
                }
                """;

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalid))
                .andExpect(status().isBadRequest());
    }
}