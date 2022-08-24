package com.example.barclayspb7d.barclays_project.dao;

import com.example.barclayspb7d.barclays_project.entities.LoanRepaymentSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepaymentRepository extends JpaRepository<LoanRepaymentSchedule, Long>{
    
    @Query("SELECT l FROM LoanRepaymentSchedule l WHERE l.mailID = ?1")
    public LoanRepaymentSchedule findByMailID(String mailID);
}
