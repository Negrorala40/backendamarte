package com.ecommerce.amarte.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.amarte.entity.Addres;
import com.ecommerce.amarte.repository.AddresRepository;

@Service
public class AddresService {

    @Autowired
    private AddresRepository addressRepository;

    // Crear o actualizar dirección
    public Addres saveOrUpdateAddress(Addres address) {
        // Aquí puedes agregar validaciones adicionales
        return addressRepository.save(address);
    }

    // Obtener todas las direcciones de un usuario
    public List<Addres> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    // Obtener una dirección por ID
    public Optional<Addres> getAddressById(Long addressId) {
        return addressRepository.findById(addressId);
    }

    // Eliminar una dirección por ID
    public void deleteAddressById(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}