package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.config.JwtUtil;
import com.ecommerce.amarte.dto.CartItemResponseDTO;
import com.ecommerce.amarte.entity.CartItem;
import com.ecommerce.amarte.entity.ProductVariant;
import com.ecommerce.amarte.repository.CartItemRepository;
import com.ecommerce.amarte.repository.ProductVariantRepository;
import com.ecommerce.amarte.repository.UserRepository;
import com.ecommerce.amarte.service.CartItemService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartItemService cartItemService;
    private final JwtUtil jwtUtil;

    public CartItemController(CartItemRepository cartItemRepository, UserRepository userRepository,
                              ProductVariantRepository productVariantRepository,
                              CartItemService cartItemService,
                              JwtUtil jwtUtil) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productVariantRepository = productVariantRepository;
        this.cartItemService = cartItemService;
        this.jwtUtil = jwtUtil;
    }

    // Obtener ítems del carrito a partir del token
    @GetMapping
    public ResponseEntity<List<CartItemResponseDTO>> getCartByToken(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        Long userId = jwtUtil.getUserId(token);

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        List<CartItemResponseDTO> response = cartItems.stream().map(item -> {
            ProductVariant variant = item.getProductVariant();
            // Forzar carga de imágenes si son lazy (opcional)
            if (variant.getImages() != null) {
                variant.getImages().size();
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
                    variant.getImages() != null
                            ? variant.getImages().stream()
                            .map(img -> img.getImageUrl())
                            .toList()
                            : List.of()
            );
        }).toList();

        return ResponseEntity.ok(response);
    }

    // Método reutilizable para extraer el token del header
    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader != null && authHeader.startsWith("Bearer ")
                ? authHeader.substring(7)
                : null;
    }

    // Agregar producto al carrito
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addToCart(@RequestParam Long userId,
                                                         @RequestParam Long productVariantId,
                                                         @RequestParam int quantity) {
        Optional<ProductVariant> productVariantOpt = productVariantRepository.findById(productVariantId);
        if (userRepository.findById(userId).isEmpty() || productVariantOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Usuario o producto no encontrado."));
        }

        if (quantity > productVariantOpt.get().getStock()) {
            return ResponseEntity.badRequest().body(Map.of("message", "La cantidad solicitada excede el stock disponible."));
        }

        cartItemService.addProductToCart(userId, productVariantId, quantity);
        return ResponseEntity.ok(Map.of("message", "Producto agregado al carrito."));
    }

    // Eliminar un ítem del carrito
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Map<String, String>> removeCartItem(@PathVariable Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            return ResponseEntity.badRequest().body(Map.of("message", "El producto en el carrito no existe."));
        }
        cartItemRepository.deleteById(cartItemId);
        return ResponseEntity.ok(Map.of("message", "Producto eliminado del carrito."));
    }

    // Vaciar carrito por usuario
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Map<String, String>> clearCart(@PathVariable Long userId) {
        cartItemRepository.deleteByUserId(userId);
        return ResponseEntity.ok(Map.of("message", "Carrito vaciado con éxito."));
    }

    // Actualizar cantidad de un ítem en el carrito
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<Map<String, String>> updateCartItemQuantity(@PathVariable Long cartItemId,
                                                                      @RequestParam int quantity) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);
        if (cartItemOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El producto no existe en el carrito."));
        }

        CartItem cartItem = cartItemOpt.get();
        if (quantity > cartItem.getProductVariant().getStock()) {
            return ResponseEntity.badRequest().body(Map.of("message", "La cantidad solicitada excede el stock disponible."));
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return ResponseEntity.ok(Map.of("message", "Cantidad actualizada con éxito."));
    }
}
