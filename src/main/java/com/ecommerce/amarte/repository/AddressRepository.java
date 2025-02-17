package com.ecommerce.amarte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.amarte.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository <Address, Long> {
    List<Address> findByUserId(long userId);

    void deleteByUserId(Long userId);
    
}
