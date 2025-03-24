package com.ecommerce.amarte.service;

import com.ecommerce.amarte.entity.UserRole;
import com.ecommerce.amarte.entity.UserRoleEnum;
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

    // Obtener un rol por ENUM en lugar de String
    public Optional<UserRole> getRoleByEnum(UserRoleEnum role) {
        return userRoleRepository.findByRole(role);
    }

    // Guardar un rol (solo si es necesario para inicializar)
    public UserRole saveRole(UserRoleEnum roleEnum) {
        // Evita duplicados: si ya existe, lo retorna
        return userRoleRepository.findByRole(roleEnum)
                .orElseGet(() -> userRoleRepository.save(new UserRole(roleEnum)));
    }
}
