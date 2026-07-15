package com.trial.demo.service;

import com.trial.demo.entity.Product;
import com.trial.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository pr;

    // Create
    public Product create(Product product) {
        return pr.save(product);
    }

    // Read All
    public List<Product> getAll() {
        return pr.findAll();
    }

    // Read By Id
    public Optional<Product> getById(Long id) {
        return pr.findById(id);
    }

    // Update
    public Product update(Long id, Product product) {
        if (pr.existsById(id)) {
            product.setId(id);   // Ensure the correct ID is updated
            return pr.save(product);
        }
        return null;
    }

    // Delete
    public boolean delete(Long id) {
        if (pr.existsById(id)) {
            pr.deleteById(id);
            return true;
        }
        return false;
    }

}
