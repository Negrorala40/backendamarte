package com.ecommerce.amarte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.amarte.entity.OrderItem;

@Repository

public interface OrderItemRepo extends JpaRepository <OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId); // Obtener items de un pedido específico
    
}
