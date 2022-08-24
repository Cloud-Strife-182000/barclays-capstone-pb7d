package com.example.barclayspb7d.barclays_project.services;

import javax.transaction.Transactional;

import com.example.barclayspb7d.barclays_project.dao.RepaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LoanRepaymentService {

	@Autowired
	RepaymentRepository LoanRepaymentRepository;
	
	public double CalcEmi(Double interestRate, Integer tenure, Long loanAmount) {
		Double monthlyInterest = interestRate/1200;
		Integer tenureInMonth = tenure*12;
		return (loanAmount*monthlyInterest*(Math.pow((1+monthlyInterest), tenureInMonth)))/(Math.pow((1+monthlyInterest), tenureInMonth-1));
	}

	public double CalcIntrest() {
		
		return 0.0;
	}
  
  	public double CalcPrincipal() {
		
		return 0.0;
	}
  	
	public double CalcOutstanding() {
		
		return 0.0;
	}

}
