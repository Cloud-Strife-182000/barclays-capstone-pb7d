package com.example.barclayspb7d.barclays_project.entities;

import javax.persistence.*;

@Entity
@Table(name = "loan_account")
public class LoanAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id", unique = true, nullable = false)
	private Long loanId;

    @Column(nullable = false, unique = true, length = 255)
    private String mailID;

	@Column(name = "max_loan_grant", nullable = false, unique = false)
	private Double maxLoanGrant;

	@Column(name = "interest_rate", nullable = false, unique = false)
	private Double interestRate = 7.0;

	@Column(name = "tenure", nullable = false, unique = false)
	private Integer tenure;

	@Column(name = "loan_amount", nullable = false, unique = false)
	private Long loanAmount;

    @Column(name = "loan_status", nullable = false, unique = false)
	private String loanStatus;

    @Column(name = "address", nullable = false, unique = false)
	private String loanAddress;

    @Column(name = "net_monthly_salary", nullable = false, unique = false)
	private Long netMonthlySalary;

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getMailID() {
        return this.mailID;
    }

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }

    public String getLoanAddress() {
        return this.loanAddress;
    }

    public void setLoanAddress(String loanAddress) {
        this.loanAddress = loanAddress;
    }

    public Long getNetMonthlySalary() {
        return this.netMonthlySalary;
    }

    public void setNetMonthlySalary(Long netMonthlySalary) {
        this.netMonthlySalary = netMonthlySalary;
    }

	@OneToOne(targetEntity = User.class)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LoanAccount() {

	}

	public LoanAccount(Double maxLoanGrant, Double interestRate, Integer tenure, Long loanAmount, String loanStatus) {
		super();
		this.maxLoanGrant = maxLoanGrant;
		this.interestRate = 7.0;
		this.tenure = tenure;
		this.loanAmount = loanAmount;
        this.loanStatus = "PENDING";
	}

	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", maxLoanGrant=" + maxLoanGrant + ", interestRate=" + interestRate
				+ ", tenure=" + tenure + ", loanAmount=" + loanAmount + "]";
	}

	public Long getLoanId() {
		return loanId;
	}

	public Double getMaxLoanGrant() {
		return maxLoanGrant;
	}

	public void setMaxLoanGrant(Double maxLoanGrant) {
		this.maxLoanGrant = maxLoanGrant;
	}

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public Long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Long loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getInterestRate() {
		return interestRate;
	}

    public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

    // enum StatusEnum {
    //     APPROVED,
    //     ONGOING,
    //     CLOSED
    // }

    // @Column(nullable = false, unique = false)
    // private StatusEnum status;
}
