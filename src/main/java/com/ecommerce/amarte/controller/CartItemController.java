package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.dto.CartItemResponseDTO;
import com.ecommerce.amarte.entity.CartItem;
import com.ecommerce.amarte.entity.User;
import com.ecommerce.amarte.entity.ProductVariant;
import com.ecommerce.amarte.dto.CartItemDTO;
import com.ecommerce.amarte.repository.CartItemRepository;
import com.ecommerce.amarte.repository.ProductVariantRepository;
import com.ecommerce.amarte.repository.UserRepository;
import com.ecommerce.amarte.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartItemService cartItemService; // Servicio para manejar la lógica

    public CartItemController(CartItemRepository cartItemRepository, UserRepository userRepository,
                              ProductVariantRepository productVariantRepository, CartItemService cartItemService) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productVariantRepository = productVariantRepository;
        this.cartItemService = cartItemService;
    }

    // Obtener los ítems del carrito de un usuario
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponseDTO>> getCartByUser(@PathVariable Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        List<CartItemResponseDTO> response = cartItems.stream().map(item -> {
            ProductVariant variant = item.getProductVariant();

            // Asegurar que se carguen relaciones (si usas JPA LAZY)
            if (variant.getProduct() != null) {
                variant.getProduct().getImages().size();
            }

            return new CartItemResponseDTO(
                    item.getId(),
                    item.getQuantity(),
                    item.getTotalPrice(),
                    variant.getId(),
                    variant.getColor(),
                    variant.getSize(),
                    variant.getStock(),
                    variant.getPrice(),
                    variant.getProduct().getId(),
                    variant.getProduct().getName(),
                    variant.getProduct().getImages().stream()
                            .map(img -> img.getImageUrl())
                            .toList()
            );
        }).toList();

        return ResponseEntity.ok(response);
    }


    // Agregar un producto al carrito
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addToCart(@RequestParam Long userId,
                                                         @RequestParam Long productVariantId,
                                                         @RequestParam int quantity) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<ProductVariant> productVariantOpt = productVariantRepository.findById(productVariantId);

        if (userOpt.isEmpty() || productVariantOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Usuario o producto no encontrado."));
        }

        ProductVariant productVariant = productVariantOpt.get();

        // Verificar si la cantidad solicitada excede el stock
        if (quantity > productVariant.getStock()) {
            return ResponseEntity.badRequest().body(Map.of("message", "La cantidad solicitada excede el stock disponible."));
        }

        // Agregar o actualizar el producto en el carrito
        CartItem cartItem = cartItemService.addProductToCart(userId, productVariantId, quantity);

        return ResponseEntity.ok(Map.of("message", "Producto agregado al carrito."));
    }

    // Eliminar un producto del carrito
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Map<String, String>> removeCartItem(@PathVariable Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            return ResponseEntity.badRequest().body(Map.of("message", "El producto en el carrito no existe."));
        }
        cartItemRepository.deleteById(cartItemId);
        return ResponseEntity.ok(Map.of("message", "Producto eliminado del carrito."));
    }

    // Vaciar el carrito de un usuario
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Map<String, String>> clearCart(@PathVariable Long userId) {
        cartItemRepository.deleteByUserId(userId);
        return ResponseEntity.ok(Map.of("message", "Carrito vaciado con éxito."));
    }

    // Actualizar la cantidad de un producto en el carrito
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<Map<String, String>> updateCartItemQuantity(@PathVariable Long cartItemId,
                                                                      @RequestParam int quantity) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);

        if (cartItemOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El producto no existe en el carrito."));
        }

        CartItem cartItem = cartItemOpt.get();
        ProductVariant productVariant = cartItem.getProductVariant();

        // Verificar que la cantidad no exceda el stock
        if (quantity > productVariant.getStock()) {
            return ResponseEntity.badRequest().body(Map.of("message", "La cantidad solicitada excede el stock disponible."));
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return ResponseEntity.ok(Map.of("message", "Cantidad actualizada con éxito."));
    }
}
