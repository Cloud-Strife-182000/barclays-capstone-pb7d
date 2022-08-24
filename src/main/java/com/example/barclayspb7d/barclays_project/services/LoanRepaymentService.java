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
	
	public static double CalcEmi(Double interestRate, Integer tenure, Long loanAmount) {

		Double monthlyInterest = interestRate/1200;
		Double tenureInMonth = Double.valueOf(tenure*12);

		Double numerator = loanAmount * monthlyInterest * (Math.pow((1+monthlyInterest), tenureInMonth));
		Double denominator = Math.pow((1+monthlyInterest), tenureInMonth) - 1;

		return numerator/denominator;
	}

	public static double CalcIntrest(Double Outstanding , Double interestRate) {
		return (Outstanding*interestRate/1200);
	}

	public static double CalcPrincipal(Double EMI,Double InterestAmount) {
		return (EMI-InterestAmount);
	}
  	
	public static double CalcOutstanding(double Outstanding, double Principal) {
		
		return(Outstanding-Principal);
	}

}
