package com.ecommerce.amarte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.amarte.entity.Order;

@Repository

public interface OrderRepository extends JpaRepository <Order, Long> {
    List<Order> findByUserId(Long userId); // Obtener pedidos de un usuario específico
    List<Order> findByStatus(String status); // Filtrar por estado del pedido
    
}
