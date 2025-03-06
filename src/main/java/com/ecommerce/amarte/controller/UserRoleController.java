package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.entity.UserRole;
import com.ecommerce.amarte.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    // Obtener todos los roles (público)
    @GetMapping
    public ResponseEntity<List<UserRole>> getAllRoles() {
        List<UserRole> roles = userRoleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // Obtener un rol específico (público)
    @GetMapping("/{role}")
    public ResponseEntity<UserRole> getRoleByName(@PathVariable String role) {
        Optional<UserRole> foundRole = userRoleService.getRoleByName(role);
        return foundRole.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo rol (solo para ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserRole> createRole(@RequestBody UserRole role) {
        UserRole newRole = userRoleService.saveRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
    }
}
