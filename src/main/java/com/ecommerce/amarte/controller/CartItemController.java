package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.entity.CartItem;
import com.ecommerce.amarte.entity.User;
import com.ecommerce.amarte.entity.ProductVariant;
import com.ecommerce.amarte.repository.CartItemRepository;
import com.ecommerce.amarte.repository.ProductVariantRepository;
import com.ecommerce.amarte.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;

    public CartItemController(CartItemRepository cartItemRepository, UserRepository userRepository, ProductVariantRepository productVariantRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productVariantRepository = productVariantRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCartByUser(@PathVariable Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long userId, @RequestParam Long productVariantId, @RequestParam int quantity) {
        Optional<User> user = userRepository.findById(userId);
        Optional<ProductVariant> productVariant = productVariantRepository.findById(productVariantId);

        if (user.isEmpty() || productVariant.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario o producto no encontrado.");
        }

        CartItem cartItem = new CartItem();
        cartItem.setUser(user.get());
        cartItem.setProductVariant(productVariant.get());
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);
        return ResponseEntity.ok("Producto agregado al carrito.");
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            return ResponseEntity.badRequest().body("El producto en el carrito no existe.");
        }
        cartItemRepository.deleteById(cartItemId);
        return ResponseEntity.ok("Producto eliminado del carrito.");
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        cartItemRepository.deleteByUserId(userId);
        return ResponseEntity.ok("Carrito vaciado con éxito.");
    }
}