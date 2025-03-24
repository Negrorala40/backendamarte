package com.ecommerce.amarte.repository;

import com.ecommerce.amarte.entity.UserRole;
import com.ecommerce.amarte.entity.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRole(UserRoleEnum role);
}
