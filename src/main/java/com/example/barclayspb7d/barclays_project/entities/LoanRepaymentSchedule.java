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

	public LoanAccount getLoanAccount() {
		return this.loanAccount;
	}

	public void setLoanAccount(LoanAccount loanAccount) {
		this.loanAccount = loanAccount;
	}

	public Double getEMI() {
		return this.EMI;
	}

	public void setEMI(Double EMI) {
		this.EMI = EMI;
	}

	public Double getPrincipalAmount() {
		return this.PrincipalAmount;
	}

	public void setPrincipalAmount(Double PrincipalAmount) {
		this.PrincipalAmount = PrincipalAmount;
	}

	public Double getOutstanding() {
		return this.Outstanding;
	}

	public void setOutstanding(Double Outstanding) {
		this.Outstanding = Outstanding;
	}

	public Double getIntrestAmount() {
		return this.IntrestAmount;
	}

	public void setIntrestAmount(Double IntrestAmount) {
		this.IntrestAmount = IntrestAmount;
	}

	public String getStatus() {
		return this.Status;
	}

	public void setStatus(String Status) {
		this.Status = Status;
	}

	public Double getMonths() {
		return this.Months;
	}

	public void setMonths(Double Months) {
		this.Months = Months;
	}


}
