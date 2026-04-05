package com.zorvyn.finance_dashboard.Security;


import com.zorvyn.finance_dashboard.Entity.Users;
import com.zorvyn.finance_dashboard.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Fetch the user from our database
        Users user = UserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 2. Translate our Role enum into a Spring Security Authority
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        // 3. Return a standard Spring Security User object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                user.isActive(), // Account active?
                true,               // Account not expired?
                true,               // Credentials not expired?
                true,               // Account not locked?
                Collections.singletonList(authority) // The user's role
        );
    }
    
}
