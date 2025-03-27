package com.ecommerce.amarte.service;

import com.ecommerce.amarte.dto.ImgDTO;
import com.ecommerce.amarte.dto.ProductDTO;
import com.ecommerce.amarte.dto.ProductVariantDTO;
import com.ecommerce.amarte.entity.Img;
import com.ecommerce.amarte.entity.Product;
import com.ecommerce.amarte.entity.ProductGender;
import com.ecommerce.amarte.entity.ProductVariant;
import com.ecommerce.amarte.entity.ProductType;
import com.ecommerce.amarte.exception.InvalidProductDataException;
import com.ecommerce.amarte.exception.ProductNotFoundException;
import com.ecommerce.amarte.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        validateProductData(productDTO);
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        validateProductData(productDTO);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setGender(getValidGender(productDTO.getGender()));
        product.setType(getValidType(productDTO.getType()));

        product.setVariants(productDTO.getVariants().stream()
                .map(this::convertToEntity)
                .peek(variant -> variant.setProduct(product))
                .collect(Collectors.toList()));

        product.setImages(productDTO.getImages().stream()
                .map(this::convertToEntity)
                .peek(img -> img.setProduct(product))
                .collect(Collectors.toList()));

        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Producto con ID " + id + " no encontrado");
        }
        productRepository.deleteById(id);
    }

    private void validateProductData(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            throw new InvalidProductDataException("El nombre del producto no puede estar vacío");
        }
    }

    private ProductGender getValidGender(String gender) {
        try {
            return ProductGender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidProductDataException("Género inválido: " + gender);
        }
    }

    private ProductType getValidType(String type) {
        try {
            return ProductType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidProductDataException("Tipo inválido: " + type);
        }
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getGender().name(),
                product.getType().name(),
                product.getVariants().stream().map(this::convertToDTO).collect(Collectors.toList()),
                product.getImages().stream().map(this::convertToDTO).collect(Collectors.toList())
        );
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setGender(getValidGender(productDTO.getGender()));
        product.setType(getValidType(productDTO.getType()));

        product.setVariants(productDTO.getVariants().stream()
                .map(this::convertToEntity)
                .peek(variant -> variant.setProduct(product))
                .collect(Collectors.toList()));

        product.setImages(productDTO.getImages().stream()
                .map(this::convertToEntity)
                .peek(img -> img.setProduct(product))
                .collect(Collectors.toList()));

        return product;
    }

    private ImgDTO convertToDTO(Img img) {
        return new ImgDTO(img.getId(), img.getFileName(), img.getImageUrl(), img.getProduct().getId());
    }

    private Img convertToEntity(ImgDTO imgDTO) {
        Img img = new Img();
        img.setId(imgDTO.getId());
        img.setFileName(imgDTO.getFileName());
        img.setImageUrl(imgDTO.getImageUrl());
        return img;
    }

    private ProductVariantDTO convertToDTO(ProductVariant variant) {
        return new ProductVariantDTO(variant.getId(), variant.getSize(), variant.getColor(), variant.getStock(),variant.getPrice(), variant.getProduct().getId());
    }

    private ProductVariant convertToEntity(ProductVariantDTO variantDTO) {
        ProductVariant variant = new ProductVariant();
        variant.setId(variantDTO.getId());
        variant.setSize(variantDTO.getSize());
        variant.setColor(variantDTO.getColor());
        variant.setStock(variantDTO.getStock());
        variant.setPrice(variantDTO.getPrice());
        return variant;
    }
}
