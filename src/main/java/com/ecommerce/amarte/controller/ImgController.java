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

    @PostMapping("/upload/{productId}")
    public ResponseEntity<Img> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("imageUrl") String imageUrl,
            @PathVariable Long productId) {
        try {
            Img savedImage = imgService.saveImage(file, imageUrl, productId);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Obtener imagen por ID
    @GetMapping("/{id}")
    public ResponseEntity<String> getImage(@PathVariable Long id) {
        Img img = imgService.getImageById(id);
        return ResponseEntity.ok(img.getImageUrl()); // Retorna la URL en lugar de bytes
    }

    // Obtener imágenes de un producto
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Img>> getImagesByProduct(@PathVariable Long productId) {
        List<Img> images = imgService.getImagesByProductId(productId);
        return ResponseEntity.ok(images);
    }

    // Actualizar imagen
    @PutMapping("/{id}")
    public ResponseEntity<Img> updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Img updatedImage = imgService.updateImage(id, file);
            return ResponseEntity.ok(updatedImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Eliminar imagen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imgService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
