package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.Img;
import com.ecommerce.amarte.entity.ProductVariant;
import com.ecommerce.amarte.repository.ImgRepository;
import com.ecommerce.amarte.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImgService {

    @Autowired
    private ImgRepository imgRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    // Guardar imagen asociada a un ProductVariant
    public Img saveImage(MultipartFile file, String imageUrl, Long productVariantId) throws IOException {
        ProductVariant variant = productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new IllegalArgumentException("Variante de producto no encontrada"));

        Img img = new Img();
        img.setFileName(file.getOriginalFilename());
        img.setImageUrl(imageUrl); // Esta URL puede venir del frontend (por ejemplo, desde un servicio de almacenamiento)
        img.setProductVariant(variant);

        return imgRepository.save(img);
    }

    // Obtener imagen por ID
    public Img getImageById(Long id) {
        return imgRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Imagen no encontrada"));
    }

    // Obtener todas las imágenes de una variante de producto
    public List<Img> getImagesByProductVariantId(Long productVariantId) {
        return imgRepository.findByProductVariantId(productVariantId);
    }

    // Actualizar imagen (solo archivo y tipo de contenido en este ejemplo)
    public Img updateImage(Long id, MultipartFile file) throws IOException {
        Img img = getImageById(id);
        img.setFileName(file.getOriginalFilename());
        img.setImageUrl(file.getContentType()); // Cambia esto si realmente quieres actualizar la URL

        return imgRepository.save(img);
    }

    // Eliminar imagen
    public void deleteImage(Long id) {
        imgRepository.deleteById(id);
    }
}
