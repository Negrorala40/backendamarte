package com.ecommerce.amarte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.amarte.entity.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Buscar una categoría por su nombre
    Optional<Category> findByName(String name);

    // Verificar si una categoría existe por su nombre
    boolean existsByName(String name);
}
