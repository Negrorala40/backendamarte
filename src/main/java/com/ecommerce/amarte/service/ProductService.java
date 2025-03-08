package com.ecommerce.amarte.service;

import com.ecommerce.amarte.dto.ImgDTO;
import com.ecommerce.amarte.dto.ProductDTO;
import com.ecommerce.amarte.dto.ProductVariantDTO;
import com.ecommerce.amarte.entity.Img;
import com.ecommerce.amarte.entity.Product;
import com.ecommerce.amarte.entity.ProductGender;
import com.ecommerce.amarte.entity.ProductVariant;
import com.ecommerce.amarte.entity.productType;
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

    // Obtener producto por ID
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Obtener productos por género y tipo
    public List<ProductDTO> getProductsByGenderAndType(ProductGender gender, productType type) {
        return productRepository.findByGenderAndTypeOrderByPriceAsc(gender, type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Guardar producto
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    // Actualizar producto
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setGender(ProductGender.valueOf(productDTO.getGender()));
            product.setType(productType.valueOf(productDTO.getType()));
            product.setPrice(productDTO.getPrice());

            // Actualizar variantes
            product.setVariants(productDTO.getVariants().stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList()));

            // Actualizar imágenes
            product.setImages(productDTO.getImages().stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList()));

            Product updatedProduct = productRepository.save(product);
            return convertToDTO(updatedProduct);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    // Eliminar producto
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
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
        product.setGender(ProductGender.valueOf(productDTO.getGender()));
        product.setType(productType.valueOf(productDTO.getType()));
        product.setPrice(productDTO.getPrice());

        product.setVariants(productDTO.getVariants().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList()));

        product.setImages(productDTO.getImages().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList()));

        return product;
    }

    // Convertir Img a ImgDTO
    private ImgDTO convertToDTO(Img img) {
        return new ImgDTO(img.getId(), img.getFileName(), img.getFileType(), img.getData(), img.getProduct().getId());

    }

    // Convertir ImgDTO a Img
    private Img convertToEntity(ImgDTO imgDTO) {
        Img img = new Img();
        img.setId(imgDTO.getId());
        img.setFileName(imgDTO.getFileName());
        img.setFileType(imgDTO.getFileType());
        img.setData(imgDTO.getData());
        return img;
    }

    // Convertir ProductVariant a ProductVariantDTO
    private ProductVariantDTO convertToDTO(ProductVariant variant) {
        return new ProductVariantDTO(variant.getId(), variant.getSize(), variant.getColor(), variant.getStock());
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
