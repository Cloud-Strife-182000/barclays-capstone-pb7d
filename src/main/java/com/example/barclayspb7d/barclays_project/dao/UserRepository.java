package com.example.barclayspb7d.barclays_project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{
    
    @Query("SELECT u FROM User u WHERE u.mailID = ?1")
    public User findByMailID(String mailID);
}