package com.example.barclayspb7d.barclays_project.entities;

import javax.persistence.*;

@Entity
@Table(name = "LoanRepaymentSchedule")
public class LoanRepaymentSchedule {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private LoanAccount loanAccount;

	@Column(name = "EMI", nullable = false, unique = false)
	private Double EMI;

	@Column(name = "Principal_Amount", nullable = false, unique = false)
	private Double PrincipalAmount;

	@Column(name = "Outstanding", nullable = false, unique = false)
	private Double Outstanding;

	@Column(name = "Intrest_Amount", nullable = false, unique = false)
	private Double IntrestAmount;

  @Column(name = "status", nullable = false, unique = false)
	private String Status;
  @Column(name = "Months", nullable = false, unique = false)
	private Double Months;
  

}
