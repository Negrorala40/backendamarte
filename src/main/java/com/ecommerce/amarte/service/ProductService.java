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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Obtener todos los productos
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 🔄 Corregido: Obtener producto por ID (devuelve Optional<ProductDTO>)
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Obtener productos por género y tipo
    public List<ProductDTO> getProductsByGenderAndType(ProductGender gender, ProductType type) {
        return productRepository.findByGenderAndTypeOrderByPriceAsc(gender, type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Guardar producto
    public ProductDTO saveProduct(ProductDTO productDTO) {
        validateProductData(productDTO);
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    // Actualizar producto
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        validateProductData(productDTO);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setGender(getValidGender(productDTO.getGender()));
        product.setType(getValidType(productDTO.getType()));
        product.setPrice(productDTO.getPrice());

        // Actualizar variantes
        product.setVariants(productDTO.getVariants().stream()
                .map(this::convertToEntity)
                .peek(variant -> variant.setProduct(product))
                .collect(Collectors.toList()));

        // Actualizar imágenes
        product.setImages(productDTO.getImages().stream()
                .map(this::convertToEntity)
                .peek(img -> img.setProduct(product))
                .collect(Collectors.toList()));

        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    // Eliminar producto
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Producto con ID " + id + " no encontrado");
        }
        productRepository.deleteById(id);
    }

    // Validar datos del producto
    private void validateProductData(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            throw new InvalidProductDataException("El nombre del producto no puede estar vacío");
        }
        if (productDTO.getPrice() < 0) {
            throw new InvalidProductDataException("El precio no puede ser negativo");
        }
    }

    // Validar y obtener Enum ProductGender
    private ProductGender getValidGender(String gender) {
        try {
            return ProductGender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidProductDataException("Género inválido: " + gender);
        }
    }

    // Validar y obtener Enum ProductType
    private ProductType getValidType(String type) {
        try {
            return ProductType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidProductDataException("Tipo inválido: " + type);
        }
    }

    // Convertir entidad a DTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getGender().name(),
                product.getType().name(),
                product.getPrice(),
                product.getVariants().stream().map(this::convertToDTO).collect(Collectors.toList()),
                product.getImages().stream().map(this::convertToDTO).collect(Collectors.toList())
        );
    }

    // Convertir DTO a entidad
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setGender(getValidGender(productDTO.getGender()));
        product.setType(getValidType(productDTO.getType()));
        product.setPrice(productDTO.getPrice());

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

    // Convertir Img a ImgDTO
    private ImgDTO convertToDTO(Img img) {
        return new ImgDTO(img.getId(), img.getFileName(), img.getImageUrl(), img.getProduct().getId());
    }

    // Convertir ImgDTO a Img
    private Img convertToEntity(ImgDTO imgDTO) {
        Img img = new Img();
        img.setId(imgDTO.getId());
        img.setFileName(imgDTO.getFileName());
        img.setImageUrl(imgDTO.getImageUrl());
        return img;
    }

    // Convertir ProductVariant a ProductVariantDTO
    private ProductVariantDTO convertToDTO(ProductVariant variant) {
        return new ProductVariantDTO(variant.getId(), variant.getSize(), variant.getColor(), variant.getStock(), variant.getProduct().getId());
    }

    // Convertir ProductVariantDTO a ProductVariant
    private ProductVariant convertToEntity(ProductVariantDTO variantDTO) {
        ProductVariant variant = new ProductVariant();
        variant.setId(variantDTO.getId());
        variant.setSize(variantDTO.getSize());
        variant.setColor(variantDTO.getColor());
        variant.setStock(variantDTO.getStock());
        return variant;
    }
}
