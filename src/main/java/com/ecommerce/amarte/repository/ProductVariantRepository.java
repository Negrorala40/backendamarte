package com.ecommerce.amarte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.amarte.entity.ProductVariant;

@Repository
public interface ProductVariantRepository extends JpaRepository <ProductVariant, Long> {
    
}
