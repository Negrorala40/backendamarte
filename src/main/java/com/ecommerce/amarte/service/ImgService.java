package com.ecommerce.amarte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.amarte.entity.Img;
import com.ecommerce.amarte.repository.ImgRepository;

@Service
public class ImgService {
    @Autowired
    private ImgRepository imgRepository;

    // Crear o actualizar una imagen
    public Img saveOrUpdateImg(Img img) {
        return imgRepository.save(img);
    }

    // Obtener todas las imágenes de un producto
    public List<Img> getImgsByProductId(Long productId) {
        return imgRepository.findByProductId(productId);
    }

    // // Obtener una imagen por ID
    // public Optional<Img> getImgById(Long imgId) {
    //     return imgRepository.findById(imgId);
    // }

    // Eliminar una imagen por ID
    public void deleteImgById(Long imgId) {
        imgRepository.deleteById(imgId);
    }

    // Eliminar todas las imágenes de un producto
    public void deleteImgsByProductId(Long productId) {
        List<Img> imgs = imgRepository.findByProductId(productId);
        imgRepository.deleteAll(imgs);
    }
}
