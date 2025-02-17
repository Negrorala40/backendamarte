package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.Order;
import com.ecommerce.amarte.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Crear o guardar un pedido
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Obtener todos los pedidos
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Obtener un pedido por ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Actualizar un pedido existente
    public Order updateOrder(Long id, Order orderDetails) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order updatedOrder = existingOrder.get();
            updatedOrder.setOrderDate(orderDetails.getOrderDate());
            updatedOrder.setStatus(orderDetails.getStatus());
            updatedOrder.setTotalPrice(orderDetails.getTotalPrice());
            updatedOrder.setUser(orderDetails.getUser());
            updatedOrder.setOrderItems(orderDetails.getOrderItems());
            updatedOrder.setShipingAddress(orderDetails.getShipingAddress());
            return orderRepository.save(updatedOrder);
        } else {
            throw new RuntimeException("Order no encontrado con ID: " + id);
        }
    }

    // Eliminar un pedido por ID
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}
