package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.Product;
import com.ecommerce.amarte.entity.ProductGender;
import com.ecommerce.amarte.entity.productType;
import com.ecommerce.amarte.repository.ProductRepository;
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

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByGenderAndType(ProductGender gender, productType type) {
        return productRepository.findByGenderAndTypeOrderByPriceAsc(gender, type);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setGender(productDetails.getGender());
            product.setType(productDetails.getType());
            product.setPrice(productDetails.getPrice());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
