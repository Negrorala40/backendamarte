package com.ecommerce.amarte.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.ecommerce.amarte.repository.UserRoleRepository;
import com.ecommerce.amarte.entity.UserRole;
import com.ecommerce.amarte.entity.UserRoleEnum;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRoleRepository roleRepository;

    @Override
    public void run(String... args) {
        for (UserRoleEnum roleEnum : UserRoleEnum.values()) {
            roleRepository.findByRole(roleEnum).orElseGet(() -> {
                UserRole newRole = new UserRole(roleEnum);
                return roleRepository.save(newRole);
            });
        }
    }
}
