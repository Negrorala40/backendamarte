package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.User;
import com.ecommerce.amarte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.ecommerce.amarte.exception.EmailAlreadyExistsException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Crear o actualizar un usuario
    public User saveOrUpdateUser(User user) {
        if (user == null || !StringUtils.hasText(user.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }
        // Verificar si el correo electrónico ya está registrado
        if (existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("El correo electrónico ya está registrado.");
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
