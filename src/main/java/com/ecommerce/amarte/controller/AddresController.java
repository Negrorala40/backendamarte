package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.entity.Addres;
import com.ecommerce.amarte.service.AddresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addres") 
public class AddresController {

    @Autowired
    private AddresService addresService;

    // Crear una nueva dirección
    @PostMapping
    public ResponseEntity<Addres> createAddres(@RequestBody Addres addres) {
        try {
            Addres createdAddres = addresService.saveOrUpdateAddress(addres);
            return new ResponseEntity<>(createdAddres, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // // Actualizar una dirección existente
    // @PutMapping("/{id}")
    // public ResponseEntity<Addres> updateAddres(@PathVariable Long id, @RequestBody Addres addresDetails) {
    //     Optional<Addres> existingAddres = addresService.getAddresById(id);
    //     if (existingAddres.isPresent()) {
    //         Addres updatedAddres = existingAddres.get();
    //         updatedAddres.setStreet(addresDetails.getStreet());
    //         updatedAddres.setCity(addresDetails.getCity());
    //         updatedAddres.setState(addresDetails.getState());
    //         updatedAddres.setZipCode(addresDetails.getZipCode());
    //         updatedAddres.setCountry(addresDetails.getCountry());
    //         return new ResponseEntity<>(addresService.saveAddres(updatedAddres), HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

    // // Obtener una dirección por ID
    // @GetMapping("/{id}")
    // public ResponseEntity<Addres> getAddresById(@PathVariable Long id) {
    //     Optional<Addres> addres = addresService.getAddresById(id);
    //     return addres.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
    //                  .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    // }

    // // Obtener todas las direcciones de un usuario por su ID
    // @GetMapping("/user/{userId}")
    // public ResponseEntity<List<Addres>> getAddresByUserId(@PathVariable Long userId) {
    //     try {
    //         List<Addres> addresList = addresService.getAddresByUserId(userId);
    //         if (addresList.isEmpty()) {
    //             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //         }
    //         return new ResponseEntity<>(addresList, HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // // Eliminar una dirección por ID
    // @DeleteMapping("/{id}")
    // public ResponseEntity<HttpStatus> deleteAddresById(@PathVariable Long id) {
    //     try {
    //         addresService.deleteAddresById(id);
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // // Eliminar todas las direcciones de un usuario
    // @DeleteMapping("/user/{userId}")
    // public ResponseEntity<HttpStatus> deleteAddresByUserId(@PathVariable Long userId) {
    //     try {
    //         addresService.deleteAddresByUserId(userId);
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}
