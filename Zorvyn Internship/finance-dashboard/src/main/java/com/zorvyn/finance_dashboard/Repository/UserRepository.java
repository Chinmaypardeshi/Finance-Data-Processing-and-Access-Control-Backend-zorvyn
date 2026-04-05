package com.zorvyn.finance_dashboard.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zorvyn.finance_dashboard.Entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    // Spring Boot's magic: Just by naming the method "findByEmail", 
    // it automatically writes the SQL query for you!
    Optional<Users> findByEmail(String email);//
    
}
