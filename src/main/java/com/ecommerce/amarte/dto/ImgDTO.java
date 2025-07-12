package com.ecommerce.amarte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor      // Constructor vacío
@AllArgsConstructor     // Constructor con todos los parámetros
public class ImgDTO {

    private Long id; // ID de la imagen

    @NotBlank(message = "El nombre del archivo no puede estar vacío")
    @Size(max = 500, message = "El nombre del archivo no debe superar los 500 caracteres")
    private String fileName; // Nombre del archivo de la imagen

    @NotBlank(message = "El tipo de archivo no puede estar vacío")
    @Size(max = 500, message = "500 max url")
    private String imageUrl;

    @NotNull(message = "Debe especificar un producto al que pertenece la imagen")
    private Long productVariantId;
}
