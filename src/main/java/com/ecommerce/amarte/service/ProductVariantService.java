package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.ProductVariant;
import com.ecommerce.amarte.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    // Crear o guardar un ProductVariant
    public ProductVariant saveProductVariant(ProductVariant productVariant) {
        return productVariantRepository.save(productVariant);
    }

    // Obtener todos los ProductVariants
    public List<ProductVariant> getAllProductVariants() {
        return productVariantRepository.findAll();
    }

    // Obtener un ProductVariant por ID
    public Optional<ProductVariant> getProductVariantById(Long id) {
        return productVariantRepository.findById(id);
    }

    // Actualizar un ProductVariant
    public ProductVariant updateProductVariant(Long id, ProductVariant productVariantDetails) {
        Optional<ProductVariant> existingProductVariant = productVariantRepository.findById(id);
        if (existingProductVariant.isPresent()) {
            ProductVariant updatedProductVariant = existingProductVariant.get();
            updatedProductVariant.setColor(productVariantDetails.getColor());
            updatedProductVariant.setSize(productVariantDetails.getSize());
            updatedProductVariant.setStock(productVariantDetails.getStock());
            updatedProductVariant.setPrice(productVariantDetails.getPrice()); // Agregado el precio aquí
            updatedProductVariant.setProduct(productVariantDetails.getProduct());
            return productVariantRepository.save(updatedProductVariant);
        } else {
            throw new RuntimeException("ProductVariant no encontrado con ID: " + id);
        }
    }

    // Eliminar un ProductVariant por ID
    public void deleteProductVariantById(Long id) {
        productVariantRepository.deleteById(id);
    }
}
