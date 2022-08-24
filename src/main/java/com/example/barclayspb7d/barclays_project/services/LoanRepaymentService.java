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
		
	}

	public double CalcIntrest() {
		
	}
  
  public double CalcPrincipal() {
		
	}
  public double CalcOutstanding() {
		
	}



}
