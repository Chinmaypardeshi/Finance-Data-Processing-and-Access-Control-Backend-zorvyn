package com.zorvyn.finance_dashboard.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.zorvyn.finance_dashboard.Entity.Users;
import com.zorvyn.finance_dashboard.Enums.Role;
import com.zorvyn.finance_dashboard.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // This method will run after the application starts.
        // Only run this if the suers table is complelety empty
        if (userRepository.count() == 0) {
            Users admin = new Users();
            admin.setEmail("admin@zorvyn.com");
            // We MUST hash the password before saving it to the database!
            admin.setPasswordHash(passwordEncoder.encode("admin123")); 
            admin.setRole(Role.ROLE_ADMIN);
            
            userRepository.save(admin);
            System.out.println("✅ Default Admin User created: admin@zorvyn.com / admin123");
        }
    }
}
