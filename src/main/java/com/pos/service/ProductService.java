package com.pos.service;

import com.pos.entity.Product;
import com.pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findByIsAvailableTrue();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndIsAvailableTrue(categoryId);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Optional<Product> existing = productRepository.findById(id);
        if (existing.isPresent()) {
            Product prod = existing.get();
            prod.setName(product.getName());
            prod.setCategory(product.getCategory());
            prod.setPrice(product.getPrice());
            prod.setIsAvailable(product.getIsAvailable());
            prod.setDescription(product.getDescription());
            prod.setImageUrl(product.getImageUrl());
            return productRepository.save(prod);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product toggleAvailability(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product prod = product.get();
            prod.setIsAvailable(!prod.getIsAvailable());
            return productRepository.save(prod);
        }
        return null;
    }
}

