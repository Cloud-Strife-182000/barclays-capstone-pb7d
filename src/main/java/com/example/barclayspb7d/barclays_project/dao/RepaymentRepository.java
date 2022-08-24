package com.example.barclayspb7d.barclays_project.dao;

import javax.transaction.Transactional;

import com.example.barclayspb7d.barclays_project.entities.LoanRepaymentSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RepaymentRepository extends JpaRepository<LoanRepaymentSchedule, Long>{
    
    @Query("SELECT l FROM LoanRepaymentSchedule l WHERE l.mailID = ?1")
    public LoanRepaymentSchedule findByMailID(String mailID);

    @Modifying
    @Transactional
    @Query("UPDATE LoanRepaymentSchedule l SET l.Outstanding = ?2 WHERE l.mailID = ?1")
    public void updateOutstandingAmount(Double newOutstanding, String mailID);
}
