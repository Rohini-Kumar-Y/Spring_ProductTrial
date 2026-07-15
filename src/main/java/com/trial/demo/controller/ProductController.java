package com.trial.demo.controller;

import com.trial.demo.entity.Product;
import com.trial.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000"})
public class ProductController {
    @Autowired
    private ProductService productService;

    // Create
    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    // Read All
    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    // Read By Id
    @GetMapping("/{id}")
    public Optional<Product> getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    // Update
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id,
                          @RequestBody Product product) {
        return productService.update(id, product);
    }

    // Delete
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return productService.delete(id);
    }
}
