package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.OrderItem;
import com.ecommerce.amarte.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Crear o guardar un OrderItem
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    // Obtener todos los OrderItems
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    // Obtener un OrderItem por ID
    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    // Actualizar un OrderItem
    public OrderItem updateOrderItem(Long id, OrderItem orderItemDetails) {
        Optional<OrderItem> existingOrderItem = orderItemRepository.findById(id);
        if (existingOrderItem.isPresent()) {
            OrderItem updatedOrderItem = existingOrderItem.get();
            updatedOrderItem.setQuantity(orderItemDetails.getQuantity());
            updatedOrderItem.setPrice(orderItemDetails.getPrice());
            updatedOrderItem.setProductVariant(orderItemDetails.getProductVariant());
            updatedOrderItem.setOrder(orderItemDetails.getOrder());
            return orderItemRepository.save(updatedOrderItem);
        } else {
            throw new RuntimeException("OrderItem no encontrado con ID: " + id);
        }
    }

    // Eliminar un OrderItem por ID
    public void deleteOrderItemById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
