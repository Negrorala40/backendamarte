// 📦 Indica el paquete donde se encuentra esta clase
package com.ecommerce.amarte.config;

// 📥 Importa las clases necesarias para la configuración y manejo de CORS
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;  // Permite crear listas fácilmente

// 🏷️ Marca esta clase como una configuración de Spring
@Configuration
public class CorsConfig {

    // 🌱 Crea un método que define las reglas de CORS y lo marca como un Bean para que Spring lo gestione
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // 🛠️ Crea un objeto para configurar CORS
        CorsConfiguration configuration = new CorsConfiguration();

        // 🌐 Permite solicitudes solo desde http://localhost:3000 (tu frontend en React)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

        // 🚦 Define qué métodos HTTP se permiten (solo estos cuatro)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        // 📦 Permite todos los tipos de encabezados HTTP (útil para tokens y JSON)
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // 🗺️ Crea un objeto para aplicar la configuración a ciertas rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 🌍 Aplica las reglas de CORS a todas las rutas de la API (/** significa todas)
        source.registerCorsConfiguration("/**", configuration);

        // 🔄 Devuelve el objeto configurado para que Spring lo use
        return source;
    }
}
