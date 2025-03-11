package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.Img;
import com.ecommerce.amarte.entity.Product;
import com.ecommerce.amarte.repository.ImgRepository;
import com.ecommerce.amarte.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImgService {

    @Autowired
    private ImgRepository imgRepository;

    @Autowired
    private ProductRepository productRepository;

    // Guardar imagen
    public Img saveImage(MultipartFile file, Long productId) throws IOException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        Img img = new Img();
        img.setFileName(file.getOriginalFilename());
        img.setImageUrl(file.getContentType());
        img.setProduct(productOptional.get());

        return imgRepository.save(img);
    }

    // Obtener imagen por ID
    public Img getImageById(Long id) {
        return imgRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Imagen no encontrada"));
    }

    // Obtener todas las imágenes de un producto
    public List<Img> getImagesByProductId(Long productId) {
        return imgRepository.findByProductId(productId);
    }

    // Actualizar imagen
    public Img updateImage(Long id, MultipartFile file) throws IOException {
        Img img = getImageById(id);
        img.setFileName(file.getOriginalFilename());
        img.setImageUrl(file.getContentType());

        return imgRepository.save(img);
    }

    // Eliminar imagen
    public void deleteImage(Long id) {
        imgRepository.deleteById(id);
    }
}
