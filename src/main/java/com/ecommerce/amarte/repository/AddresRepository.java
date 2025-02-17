package com.ecommerce.amarte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.amarte.entity.Addres;

@Repository
public interface AddresRepository extends JpaRepository <Addres, Long> {
    List<Addres> findByUserId(long userId);

    
    
}
