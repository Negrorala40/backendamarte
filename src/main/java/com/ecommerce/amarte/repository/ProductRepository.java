package com.ecommerce.amarte.repository;

import com.ecommerce.amarte.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Método para buscar productos por categoría
    List<Product> findByCategoryId(Long categoryId);

    // Método para buscar productos por género
    List<Product> findByGender(String gender);

    // Método para buscar productos por tipo
    List<Product> findByType(String type);

    // Método para realizar búsquedas por nombre (similar)
    List<Product> findByNameContainingIgnoreCase(String name);
}
