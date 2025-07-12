package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.dto.ProductVariantDTO;
import com.ecommerce.amarte.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-variants")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    // Crear un nuevo ProductVariant
    @PostMapping
    public ResponseEntity<ProductVariantDTO> createProductVariant(@RequestBody ProductVariantDTO productVariantDTO) {
        try {
            ProductVariantDTO createdProductVariant = productVariantService.saveProductVariant(productVariantDTO);
            return new ResponseEntity<>(createdProductVariant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los ProductVariants
    @GetMapping
    public ResponseEntity<List<ProductVariantDTO>> getAllProductVariants() {
        try {
            List<ProductVariantDTO> productVariants = productVariantService.getAllProductVariants();
            if (productVariants.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productVariants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener un ProductVariant por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantDTO> getProductVariantById(@PathVariable Long id) {
        Optional<ProductVariantDTO> productVariant = productVariantService.getProductVariantById(id);
        return productVariant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar un ProductVariant existente
    @PutMapping("/{id}")
    public ResponseEntity<ProductVariantDTO> updateProductVariant(@PathVariable Long id, @RequestBody ProductVariantDTO productVariantDTO) {
        try {
            ProductVariantDTO updatedProductVariant = productVariantService.updateProductVariant(id, productVariantDTO);
            return new ResponseEntity<>(updatedProductVariant, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un ProductVariant por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProductVariant(@PathVariable Long id) {
        try {
            productVariantService.deleteProductVariantById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
