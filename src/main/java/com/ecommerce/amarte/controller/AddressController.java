package com.ecommerce.amarte.controller;

import com.ecommerce.amarte.entity.Address;
import com.ecommerce.amarte.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Crear una nueva dirección
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        try {
            Address createdAddress = addressService.saveOrUpdateAddress(address);
            return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar una dirección existente
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address addressDetails) {
        try {
            addressDetails.setId(id);  // Aseguramos que se esté actualizando la dirección con el ID correcto
            Address updatedAddress = addressService.saveOrUpdateAddress(addressDetails);
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // En caso de que la dirección no exista
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener una dirección por ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Optional<Address> address = addressService.getAddressById(id);
        return address.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener todas las direcciones de un usuario por su ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable Long userId) {
        try {
            List<Address> addressList = addressService.getAddressesByUserId(userId);
            if (addressList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(addressList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar una dirección por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAddressById(@PathVariable Long id) {
        try {
            addressService.deleteAddressById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar todas las direcciones de un usuario
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<HttpStatus> deleteAddressesByUserId(@PathVariable Long userId) {
        try {
            addressService.deleteAddressesByUserId(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
