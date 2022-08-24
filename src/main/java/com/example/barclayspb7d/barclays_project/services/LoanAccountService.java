package com.example.barclayspb7d.barclays_project.services;

import javax.transaction.Transactional;

import com.example.barclayspb7d.barclays_project.dao.LoanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LoanAccountService {

	@Autowired
	LoanRepository LoanRepository;
	
	public static double CalcEmi(Double interestRate, Integer tenure, Long loanAmount) {
		
		Double monthlyInterest = interestRate/1200;
		Double tenureInMonth = Double.valueOf(tenure*12);

		Double numerator = loanAmount * monthlyInterest * (Math.pow((1+monthlyInterest), tenureInMonth));
		Double denominator = Math.pow((1+monthlyInterest), tenureInMonth-1);

		return numerator/denominator;
	}

	public static double LoanPrePayment(Double PrePaymentAmount , Double Outstanding ) {
        
		return (Outstanding - PrePaymentAmount);
    }

    public static double LoanForeclosure(Double EMI,Double InterestAmount) {
        
		return 0.0;
    }
  	
}
