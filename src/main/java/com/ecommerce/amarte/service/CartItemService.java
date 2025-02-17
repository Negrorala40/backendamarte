package com.ecommerce.amarte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.amarte.entity.CartItem;
import com.ecommerce.amarte.repository.CartItemRepository;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    // Obtener los ítems del carrito por usuario
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    // Agregar o actualizar un producto en el carrito
    public CartItem saveOrUpdateCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    // Cambiar cantidad de un producto en el carrito
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado en el carrito."));
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    // Eliminar un producto específico del carrito
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // Vaciar el carrito de un usuario
    public void clearCartByUserId(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
