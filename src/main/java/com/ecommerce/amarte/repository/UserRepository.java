package com.ecommerce.amarte.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.amarte.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    
    @EntityGraph(attributePaths = {"orders", "cartItems", "addresses"})
    Optional<User> findByEmail(String email);

    
    
}
