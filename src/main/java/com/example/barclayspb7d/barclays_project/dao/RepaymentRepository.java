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
    @Query("UPDATE LoanRepaymentSchedule l SET l.Outstanding = ?1 WHERE l.mailID = ?2")
    public void updateOutstandingAmount(Double newOutstanding, String mailID);

    @Modifying
    @Transactional
    @Query("UPDATE LoanRepaymentSchedule l SET l.PrincipalAmount = ?1 WHERE l.mailID = ?2")
    public void updatePrincipalAmount(Double newPrincipalAmount, String mailID);

    @Modifying
    @Transactional
    @Query("UPDATE LoanRepaymentSchedule l SET l.InterestAmount = ?1 WHERE l.mailID = ?2")
    public void updateInterestAmount(Double newInterestAmount, String mailID);

    @Modifying
    @Transactional
    @Query("UPDATE LoanRepaymentSchedule l SET l.Months = ?1 WHERE l.mailID = ?2")
    public void updateMonths(Long newMonths, String mailID);

    @Modifying
    @Transactional
    @Query("UPDATE LoanRepaymentSchedule l SET l.Status = ?1 WHERE l.mailID = ?2")
    public void updateStatus(String newStatus, String mailID);
}
