package com.ecommerce.amarte.service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.amarte.entity.Address;
import com.ecommerce.amarte.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // Crear o actualizar dirección
    @Transactional
    public Address saveOrUpdateAddress(Address address) {
        try {
            // Verificar si la dirección tiene un ID (si es una actualización)
            if (address.getId() != null) {
                Optional<Address> existingAddress = addressRepository.findById(address.getId());
                if (existingAddress.isEmpty()) {
                    throw new IllegalArgumentException("La dirección con ID " + address.getId() + " no existe.");
                }
            }
            return addressRepository.save(address);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar o actualizar la dirección: " + e.getMessage());
        }
    }

    // Obtener todas las direcciones de un usuario
    public List<Address> getAddressesByUserId(Long userId) {
        try {
            return addressRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las direcciones: " + e.getMessage());
        }
    }

    // Obtener una dirección por ID
    public Optional<Address> getAddressById(Long addressId) {
        try {
            return addressRepository.findById(addressId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la dirección: " + e.getMessage());
        }
    }

    // Eliminar una dirección por ID
    public void deleteAddressById(Long addressId) {
        try {
            addressRepository.deleteById(addressId);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la dirección con ID " + addressId + ": " + e.getMessage());
        }
    }

    // Eliminar todas las direcciones de un usuario
    public void deleteAddressesByUserId(Long userId) {
        try {
            addressRepository.deleteByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar las direcciones del usuario con ID " + userId + ": " + e.getMessage());
        }
    }
}