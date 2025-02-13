package com.ecommerce.amarte.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID de la imagen

    private String fileName; // Nombre del archivo de la imagen
    private String fileType; // Tipo de archivo (ej. image/jpeg, image/png)

    private byte[] data; // Aquí se guardan los bytes de la imagen

    @ManyToOne
    @JoinColumn(name = "product_id") // Definimos el nombre de la columna que hace referencia a Product
    private Product product; // Relación con el Producto
}
