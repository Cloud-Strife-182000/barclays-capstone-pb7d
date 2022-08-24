package com.example.barclayspb7d.barclays_project.entities;

import javax.persistence.*;

@Entity
@Table(name = "LoanRepaymentSchedule")
public class LoanRepaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", unique = true, nullable = false)
	private Long scheduleId;

    @Column(nullable = false, unique = true, length = 255)
    private String mailID;

	@Column(name = "EMI", nullable = false, unique = false)
	private Double EMI;

	@Column(name = "Principal_Amount", nullable = false, unique = false)
	private Double PrincipalAmount;

	@Column(name = "Outstanding", nullable = false, unique = false)
	private Double Outstanding;

	@Column(name = "Interest_Amount", nullable = false, unique = false)
	private Double InterestAmount;

  	@Column(name = "status", nullable = false, unique = false)
	private String Status;
  	
	@Column(name = "Months", nullable = false, unique = false)
	private Long Months;

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

	public Double getInterestAmount() {
		return this.InterestAmount;
	}

	public void setInterestAmount(Double InterestAmount) {
		this.InterestAmount = InterestAmount;
	}

	public String getStatus() {
		return this.Status;
	}

	public void setStatus(String Status) {
		this.Status = Status;
	}

	public Long getMonths() {
		return this.Months;
	}

	public void setMonths(Long Months) {
		this.Months = Months;
	}


	public Long getScheduleId() {
		return this.scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getMailID() {
		return this.mailID;
	}

	public void setMailID(String mailID) {
		this.mailID = mailID;
	}



}
