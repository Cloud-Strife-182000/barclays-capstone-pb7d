package com.example.barclayspb7d.barclays_project.dao;

import javax.transaction.Transactional;

import com.example.barclayspb7d.barclays_project.entities.LoanAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LoanRepository extends JpaRepository<LoanAccount, Long>{
    
    @Query("SELECT l FROM LoanAccount l WHERE l.mailID = ?1")
    public LoanAccount findByMailID(String mailID);

    @Modifying
    @Transactional
    @Query("UPDATE LoanAccount l SET l.loanStatus = ?1 WHERE l.mailID = ?2")
    public void updateStatus(String newStatus, String mailID);
}