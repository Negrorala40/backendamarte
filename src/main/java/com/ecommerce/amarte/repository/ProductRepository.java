package com.ecommerce.amarte.repository;

import com.ecommerce.amarte.entity.Product;
import com.ecommerce.amarte.entity.ProductGender;
import com.ecommerce.amarte.entity.ProductType;
import com.ecommerce.amarte.entity.ProductVariant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Método para buscar productos por género y tipo
    List<Product> findByGenderAndType(ProductGender gender, ProductType type);

    // Método para buscar productos por tipo
    List<Product> findByType(ProductType type);

    // Método para realizar búsquedas por nombre (similar)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Buscar productos filtrados por género y tipo, y ordenados por precio (ahora en ProductVariant)
    List<Product> findByGenderAndTypeOrderByVariantsPriceAsc(ProductGender gender, ProductType type);

    // Buscar productos filtrados por género y ordenados por precio (ahora en ProductVariant)
    List<Product> findByGenderOrderByVariantsPriceAsc(ProductGender gender);

    // Buscar productos filtrados por género y tipo en un rango de precio (ahora en ProductVariant)
    List<Product> findByGenderAndTypeAndVariantsPriceBetween(ProductGender gender, ProductType type, double minPrice, double maxPrice);

    // Buscar productos filtrados por género y tipo, ordenados por precio descendente (ahora en ProductVariant)
    List<Product> findByGenderAndTypeOrderByVariantsPriceDesc(ProductGender gender, ProductType type);
}
