package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.User;
import com.ecommerce.amarte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.ecommerce.amarte.exception.EmailAlreadyExistsException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Instancia de BCrypt
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Crear o actualizar un usuario con contraseña encriptada
    public User saveOrUpdateUser(User user) {
        if (user == null || !StringUtils.hasText(user.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }

        // Verificar si el correo ya está registrado
        if (existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("El correo electrónico ya está registrado.");
        }

        // Encriptar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // Autenticar usuario con email y contraseña
    public boolean authenticateUser(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(rawPassword, user.getPassword()); // Comparar contraseña encriptada
        }
        return false;
    }

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener un usuario por ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // Obtener un usuario por correo electrónico
    public Optional<User> getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    // Eliminar un usuario por ID
    public void deleteUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
        userRepository.deleteById(userId);
    }

    // Eliminar un usuario por correo electrónico
    public void deleteUserByEmail(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
        userRepository.deleteByEmail(userEmail);
    }

    // Eliminar todas las direcciones de un usuario
    public void deleteAllAddressesByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
        user.get().getAddresses().clear();  // Elimina las direcciones del usuario
        userRepository.save(user.get());   // Guardar los cambios
    }
}
