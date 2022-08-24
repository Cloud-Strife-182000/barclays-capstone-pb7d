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
	
	public double CalcEmi() {
		
		return 0.0;
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
