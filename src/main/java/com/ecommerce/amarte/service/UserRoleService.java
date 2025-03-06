package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.UserRole;
import com.ecommerce.amarte.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    // Obtener todos los roles
    public List<UserRole> getAllRoles() {
        return userRoleRepository.findAll();
    }

    // Obtener un rol por nombre
    public Optional<UserRole> getRoleByName(String role) {
        return userRoleRepository.findByRole(role);
    }

    // Guardar un rol (solo si lo necesitas para inicializar los roles)
    public UserRole saveRole(UserRole role) {
        return userRoleRepository.save(role);
    }
}
