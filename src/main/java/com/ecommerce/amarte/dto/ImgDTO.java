package com.ecommerce.amarte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ImgDTO {

    private Long id; // ID de la imagen

    @NotBlank(message = "El nombre del archivo no puede estar vacío")
    @Size(max = 255, message = "El nombre del archivo no debe superar los 255 caracteres")
    private String fileName; // Nombre del archivo de la imagen

    @NotBlank(message = "El tipo de archivo no puede estar vacío")
    @Pattern(regexp = "^(image/jpeg|image/png|image/gif)$", message = "Formato de imagen no válido. Solo se permiten JPEG, PNG o GIF")
    private String fileType; // Tipo de archivo (ej. image/jpeg, image/png)

    @NotNull(message = "La imagen no puede estar vacía")
    private byte[] data; // Datos binarios de la imagen

    @NotNull(message = "Debe especificar un producto al que pertenece la imagen")
    private Long productId; // ID del producto asociado
}