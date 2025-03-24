package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.User;
import com.ecommerce.amarte.entity.UserRole;
import com.ecommerce.amarte.entity.UserRoleEnum;
import com.ecommerce.amarte.repository.UserRepository;
import com.ecommerce.amarte.repository.UserRoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.ecommerce.amarte.exception.EmailAlreadyExistsException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Crear o actualizar un usuario con contraseña encriptada y asignación de rol por defecto
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

        // Si el usuario no tiene roles asignados, asignar CUSTOMER por defecto
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            UserRole defaultRole = userRoleRepository.findByRole(UserRoleEnum.CUSTOMER)
                    .orElseGet(() -> userRoleRepository.save(new UserRole(UserRoleEnum.CUSTOMER)));

            user.setRoles(Collections.singleton(defaultRole));
        }

        return userRepository.save(user);
    }

    // Autenticar usuario con email y contraseña
    public boolean authenticateUser(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
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
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        userRepository.deleteById(userId);
    }

    // Eliminar un usuario por correo electrónico
    public void deleteUserByEmail(String userEmail) {
        userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        userRepository.deleteByEmail(userEmail);
    }

    // Eliminar todas las direcciones de un usuario
    public void deleteAllAddressesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        user.getAddresses().clear();
        userRepository.save(user);
    }
}
