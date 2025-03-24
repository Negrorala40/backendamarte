package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.entity.UserRoleEnum;
import com.ecommerce.amarte.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    // Obtener todos los roles disponibles (público)
    @GetMapping
    public ResponseEntity<List<UserRoleEnum>> getAllRoles() {
        return ResponseEntity.ok(Arrays.asList(UserRoleEnum.values()));
    }

    // Verificar si un rol específico existe (público)
    @GetMapping("/{role}")
    public ResponseEntity<Boolean> checkRoleExists(@PathVariable UserRoleEnum role) {
        boolean exists = Arrays.asList(UserRoleEnum.values()).contains(role);
        return ResponseEntity.ok(exists);
    }

    // No se necesita un método para crear roles, ya que están definidos en el enum
}
