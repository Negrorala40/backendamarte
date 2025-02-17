package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.User;
import com.ecommerce.amarte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Crear o actualizar un usuario
    public User saveOrUpdateUser(User user) {
        if (user == null || !StringUtils.hasText(user.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }
        return userRepository.save(user);
    }

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener un usuario por ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // Eliminar un usuario por ID
    public void deleteUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
        userRepository.deleteById(userId);
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
