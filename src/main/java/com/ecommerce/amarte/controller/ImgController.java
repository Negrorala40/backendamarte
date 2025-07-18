package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.entity.Img;
import com.ecommerce.amarte.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*") // Permite peticiones desde el frontend
public class ImgController {

    @Autowired
    private ImgService imgService;

    // Subir imagen asociada a un ProductVariant
    @PostMapping("/upload/{productVariantId}")
    public ResponseEntity<Img> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("imageUrl") String imageUrl,
            @PathVariable Long productVariantId) {
        try {
            Img savedImage = imgService.saveImage(file, imageUrl, productVariantId);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Obtener imagen por ID (retorna URL)
    @GetMapping("/{id}")
    public ResponseEntity<String> getImage(@PathVariable Long id) {
        Img img = imgService.getImageById(id);
        if (img == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(img.getImageUrl());
    }

    // Obtener imágenes de un ProductVariant
    @GetMapping("/product-variant/{productVariantId}")
    public ResponseEntity<List<Img>> getImagesByProductVariant(@PathVariable Long productVariantId) {
        List<Img> images = imgService.getImagesByProductVariantId(productVariantId);
        if (images == null || images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(images);
    }

    // Actualizar imagen (por ID)
    @PutMapping("/{id}")
    public ResponseEntity<Img> updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Img updatedImage = imgService.updateImage(id, file);
            return ResponseEntity.ok(updatedImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Eliminar imagen por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imgService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
