package com.ecommerce.amarte.service;

import java.util.List;

import com.ecommerce.amarte.exception.StockExceededException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.amarte.entity.CartItem;
import com.ecommerce.amarte.entity.ProductVariant;
import com.ecommerce.amarte.entity.User;
import com.ecommerce.amarte.repository.CartItemRepository;
import com.ecommerce.amarte.repository.ProductVariantRepository;
import com.ecommerce.amarte.repository.UserRepository;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private UserRepository userRepository;

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

    // Método especializado para agregar un producto al carrito
    public CartItem addProductToCart(Long userId, Long productVariantId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        ProductVariant productVariant = productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new EntityNotFoundException("Variante de producto no encontrada"));

        // Verificar que la cantidad no exceda el stock
        if (quantity > productVariant.getStock()) {
            throw new StockExceededException("Cantidad solicitada excede el stock disponible.");
        }

        CartItem existingCartItem = cartItemRepository.findByUserAndProductVariant(user, productVariant);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            return cartItemRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setUser(user);
            newCartItem.setProductVariant(productVariant);
            newCartItem.setQuantity(quantity);
            return cartItemRepository.save(newCartItem);
        }
    }

}
