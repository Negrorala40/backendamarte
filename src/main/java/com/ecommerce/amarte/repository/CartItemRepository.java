package com.ecommerce.amarte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.amarte.entity.CartItem;
import com.ecommerce.amarte.entity.User;
import com.ecommerce.amarte.entity.ProductVariant;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

   List<CartItem> findByUserId(Long userId);

   List<CartItem> deleteByUserId(Long userId);

   // Método para encontrar un CartItem por usuario y variante de producto
   CartItem findByUserAndProductVariant(User user, ProductVariant productVariant);
}
