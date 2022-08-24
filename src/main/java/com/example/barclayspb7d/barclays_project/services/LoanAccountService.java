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
		Double monthlyInterest = interestRate/12;
		Integer tenureInMonth = tenure*12;
		return (loanAmount*monthlyInterest*(Math.pow((1+monthlyInterest), tenureInMonth)))/(Math.pow((1+monthlyInterest), tenureInMonth-1));
	}

	public double LoanPrePayment(Double EMI , Double Outstanding ) {
                return 0.0;
        }

        public double LoanForeclosure(Double EMI,Double InterestAmount) {
                return 0.0;
        }
  	
}
