package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.entity.User;
import com.ecommerce.amarte.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //usuario por id
    // Obtener un usuario por su ID
@GetMapping("/id/{id}")
public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Usuario no encontrado
            }
                return new ResponseEntity<>(user.get(), HttpStatus.OK); // Usuario encontrado
        } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Error interno
        }
    }

    // Obtener un usuario por su correo electrónico
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            Optional<User> user = userService.getUserByEmail(email);
            if (user.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Usuario no encontrado
            }
            return new ResponseEntity<>(user.get(), HttpStatus.OK); // Usuario encontrado
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Error interno
        }
    }


    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.saveOrUpdateUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Bad Request para validaciones
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Error interno
        }
    }

    // Actualizar un usuario
    @PutMapping("/id/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        try {
            Optional<User> existingUser = userService.getUserById(id);
            if (existingUser.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Usuario no encontrado
            }
            user.setId(id); // Asegura que el id en el objeto de la solicitud sea el correcto
            User updatedUser = userService.saveOrUpdateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Bad Request
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Error interno
        }
    }

    // Actualizar un usuario por su correo electrónico
    @PutMapping("/email/{email}")
    public ResponseEntity<User> updateUserEmail(@PathVariable String email, @Valid @RequestBody User user) {
        try {
            Optional<User> existingUser = userService.getUserByEmail(email);
            if (existingUser.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Usuario no encontrado
            }
            user.setEmail(email); // Asegura que el id en el objeto de la solicitud sea el correcto
            User updatedUser = userService.saveOrUpdateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Bad Request
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Error interno
        }
    }

    // Eliminar un usuario
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Sin contenido si la eliminación es exitosa
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Usuario no encontrado
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error interno
        }
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        try {
            userService.deleteUserByEmail(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Sin contenido si la eliminación es exitosa
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Usuario no encontrado
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error interno
        }
    }

    // Eliminar todas las direcciones de un usuario
    @DeleteMapping("/id/{id}/addresses")
    public ResponseEntity<Void> deleteAllAddresses(@PathVariable Long id) {
        try {
            userService.deleteAllAddressesByUserId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Sin contenido si la eliminación es exitosa
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Usuario no encontrado
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error interno
        }
    }

}
