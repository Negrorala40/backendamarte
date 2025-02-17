package com.ecommerce.amarte.repository;

import com.ecommerce.amarte.entity.Product;
import com.ecommerce.amarte.entity.ProductGender;
import com.ecommerce.amarte.entity.productType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Método para buscar productos por género
    List<Product> findByGenderAndType(ProductGender gender, String type);

    // Método para buscar productos por tipo
    List<Product> findByType(String type);

    // Método para realizar búsquedas por nombre (similar)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Buscar productos filtrados por género y tipo, y ordenados por precio
    List<Product> findByGenderAndTypeOrderByPriceAsc(ProductGender gender, productType type);

    // Buscar productos filtrados por género y ordenados por precio
    List<Product> findByGenderOrderByPriceAsc(ProductGender gender);

    List<Product> findByGenderAndType(ProductGender gender, productType type);

    List<Product> findByGenderAndTypeAndPriceBetween(ProductGender gender, productType type, double minPrice, double maxPrice);

    List<Product> findByGenderAndTypeOrderByPriceDesc(ProductGender gender, productType type);

}