package com.ecommerce.amarte.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ecommerce.amarte.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {
    // Filtrar por género (Hombre/Mujer)
    Page<Product> findByGender(String gender, Pageable pageable);

    // Filtrar por categoría específica
    Page<Product> findByCategoryName(String type, Pageable pageable);

    // Filtrar por género y categoría (Superior/Inferior)
    Page<Product> findByGenderAndCategoryName(String gender, String type, Pageable pageable);

    // Filtrar por rango de precios
    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    //filtrar por nombre
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
}
