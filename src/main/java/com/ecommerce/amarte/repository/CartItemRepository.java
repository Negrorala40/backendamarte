package com.ecommerce.amarte.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.amarte.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository <CartItem, Long> {
   List<CartItem> findByUserId(Long userId);

   List<CartItem> deleteByUserId(Long userId);


    
}
