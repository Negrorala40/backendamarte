package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.Product;
import com.ecommerce.amarte.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Guardar un producto
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Obtener un producto por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Obtener productos por categoría
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Obtener productos por género
    public List<Product> getProductsByGender(String gender) {
        return productRepository.findByGender(gender);
    }

    // Obtener productos por tipo
    public List<Product> getProductsByType(String type) {
        return productRepository.findByType(type);
    }

    // Buscar productos por nombre
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Actualizar un producto existente
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(productDetails.getName());
            updatedProduct.setDescription(productDetails.getDescription());
            updatedProduct.setGender(productDetails.getGender());
            updatedProduct.setType(productDetails.getType());
            updatedProduct.setPrice(productDetails.getPrice());
            // updatedProduct.setCategory(productDetails.getCategory());
            updatedProduct.setVariants(productDetails.getVariants());
            updatedProduct.setImages(productDetails.getImages());
            return productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
    }

    // Eliminar un producto
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
