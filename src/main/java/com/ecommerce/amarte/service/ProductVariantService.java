package com.ecommerce.amarte.service;

import com.ecommerce.amarte.dto.ImgDTO;
import com.ecommerce.amarte.dto.ProductVariantDTO;
import com.ecommerce.amarte.entity.Img;
import com.ecommerce.amarte.entity.Product;
import com.ecommerce.amarte.entity.ProductVariant;
import com.ecommerce.amarte.exception.ProductNotFoundException;
import com.ecommerce.amarte.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    // Guardar un ProductVariant (con imágenes si vienen en el DTO)
    public ProductVariantDTO saveProductVariant(ProductVariantDTO variantDTO) {
        ProductVariant variant = convertToEntity(variantDTO);
        ProductVariant saved = productVariantRepository.save(variant);
        return convertToDTO(saved);
    }

    // Obtener todos los ProductVariants
    public List<ProductVariantDTO> getAllProductVariants() {
        return productVariantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener un ProductVariant por ID
    public Optional<ProductVariantDTO> getProductVariantById(Long id) {
        return productVariantRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Actualizar un ProductVariant por ID
    public ProductVariantDTO updateProductVariant(Long id, ProductVariantDTO variantDTO) {
        ProductVariant existing = productVariantRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Variante no encontrada con ID: " + id));

        existing.setColor(variantDTO.getColor());
        existing.setSize(variantDTO.getSize());
        existing.setStock(variantDTO.getStock());
        existing.setPrice(variantDTO.getPrice());

        // Si vienen imágenes nuevas en el DTO, las reemplazamos
        if (variantDTO.getImages() != null) {
            List<Img> updatedImages = variantDTO.getImages().stream()
                    .map(this::convertToEntity)
                    .peek(img -> img.setProductVariant(existing))
                    .collect(Collectors.toList());
            existing.setImages(updatedImages);
        }

        ProductVariant updated = productVariantRepository.save(existing);
        return convertToDTO(updated);
    }

    // Eliminar un ProductVariant
    public void deleteProductVariantById(Long id) {
        productVariantRepository.deleteById(id);
    }

    // ==========================
    // Conversión DTO <-> Entidad
    // ==========================

    private ProductVariantDTO convertToDTO(ProductVariant variant) {
        return new ProductVariantDTO(
                variant.getId(),
                variant.getSize(),
                variant.getColor(),
                variant.getStock(),
                variant.getPrice(),
                variant.getProduct() != null ? variant.getProduct().getId() : null,
                variant.getImages() != null
                        ? variant.getImages().stream().map(this::convertToDTO).collect(Collectors.toList())
                        : null
        );
    }

    private ProductVariant convertToEntity(ProductVariantDTO dto) {
        ProductVariant variant = new ProductVariant();
        variant.setId(dto.getId());
        variant.setSize(dto.getSize());
        variant.setColor(dto.getColor());
        variant.setStock(dto.getStock());
        variant.setPrice(dto.getPrice());

        // Solo seteamos el ID del producto (no buscamos la entidad completa aquí)
        Product product = new Product();
        product.setId(dto.getProductId());
        variant.setProduct(product);

        if (dto.getImages() != null) {
            List<Img> images = dto.getImages().stream()
                    .map(this::convertToEntity)
                    .peek(img -> img.setProductVariant(variant))
                    .collect(Collectors.toList());
            variant.setImages(images);
        }

        return variant;
    }

    private ImgDTO convertToDTO(Img img) {
        return new ImgDTO(
                img.getId(),
                img.getFileName(),
                img.getImageUrl(),
                img.getProductVariant() != null ? img.getProductVariant().getId() : null
        );
    }

    private Img convertToEntity(ImgDTO dto) {
        Img img = new Img();
        img.setId(dto.getId());
        img.setFileName(dto.getFileName());
        img.setImageUrl(dto.getImageUrl());
        return img;
    }
}
