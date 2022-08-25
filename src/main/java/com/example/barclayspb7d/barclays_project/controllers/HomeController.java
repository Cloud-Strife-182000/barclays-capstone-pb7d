package com.example.barclayspb7d.barclays_project.controllers;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.barclayspb7d.barclays_project.dao.LoanRepository;
import com.example.barclayspb7d.barclays_project.dao.RepaymentRepository;
import com.example.barclayspb7d.barclays_project.dao.UserRepository;
import com.example.barclayspb7d.barclays_project.entities.EmailDetails;
import com.example.barclayspb7d.barclays_project.entities.ErrorMessage;
import com.example.barclayspb7d.barclays_project.entities.LoanAccount;
import com.example.barclayspb7d.barclays_project.entities.LoanRepaymentSchedule;
import com.example.barclayspb7d.barclays_project.entities.ScheduleEntry;
import com.example.barclayspb7d.barclays_project.entities.User;
import com.example.barclayspb7d.barclays_project.services.EmailService;
import com.example.barclayspb7d.barclays_project.services.LoanAccountService;
import com.example.barclayspb7d.barclays_project.services.LoanRepaymentService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LoanRepository loanRepo;

    @Autowired
    private RepaymentRepository scheduleRepo;

    @Autowired 
    private EmailService emailService;

    private List<ScheduleEntry> scheduleList = new ArrayList<ScheduleEntry>();

    @GetMapping("/")
    public String redirectToHomePage(){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(Model model, HttpSession session){

        User currUser = (User) session.getAttribute("curr_user");

        if(currUser != null){

            model.addAttribute("userExists", true);
        }
        else{
            model.addAttribute("userExists", false);
        }

        return "home";
    
    }

    @GetMapping("/register")
    public String registrationPage(HttpSession session, Model model){

        User currUser = (User) session.getAttribute("curr_user");

        if(currUser != null){
            
            return "redirect:/account";
        }

        model.addAttribute("register", new User());
        model.addAttribute("errorMessages", new ErrorMessage());

        return "register";
    
    }

    @PostMapping("/register")
    public String registrationFormSubmit(@ModelAttribute User user, @ModelAttribute ErrorMessage errorMessages, Model model){

        model.addAttribute("register", user);

        String enteredmailID = user.getMailID();

        User userData = userRepo.findByMailID(enteredmailID);

        if(userData != null){

            errorMessages.setErrorMessage("Error: Email already taken.");

            model.addAttribute("register", user);
            model.addAttribute("errorMessages", errorMessages);

            return "register";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "redirect:/login";

    }

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model){

        User currUser = (User) session.getAttribute("curr_user");

        if(currUser != null){
            
            return "redirect:/account";
        }

        model.addAttribute("login", new User());
        model.addAttribute("errorMessages", new ErrorMessage());

        return "login";
    
    }

    @PostMapping("/login")
    public String loginFormSubmit(@ModelAttribute User user, @ModelAttribute ErrorMessage errorMessages, HttpSession session, Model model){

        model.addAttribute("login", user);

        boolean successfulLogin = false;

        User userData = userRepo.findByMailID(user.getMailID());

        if(userData == null){

            errorMessages.setErrorMessage("Error: User not found.");

            model.addAttribute("login", user);
            model.addAttribute("errorMessages", errorMessages);

            return "login";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String enteredPassword = user.getPassword();
        String actualPassword = userData.getPassword();

        successfulLogin = passwordEncoder.matches(enteredPassword, actualPassword);

        session.setAttribute("curr_user", userData);

        if(successfulLogin){

            return "redirect:/account";
        }
        else{

            errorMessages.setErrorMessage("Error: Wrong password.");

            model.addAttribute("login", user);
            model.addAttribute("errorMessages", errorMessages);

            return "login";
        }
    
    }

    @GetMapping("/account")
    public String accountPage(HttpSession session, Model model){

        User currUser = (User) session.getAttribute("curr_user");

        if(currUser != null){

            LoanAccount existingLoanAccount = loanRepo.findByMailID(currUser.getMailID());
            
            model.addAttribute("curr_account", currUser);

            if(existingLoanAccount != null){

                scheduleList.clear();

                model.addAttribute("loanAccountExists", true);
                model.addAttribute("curr_loanAccount", existingLoanAccount);

                LoanRepaymentSchedule existingSchedule = scheduleRepo.findByMailID(currUser.getMailID());

                long months = existingSchedule.getMonths();
                long updatedMonths = existingSchedule.getMonths();

                ScheduleEntry se = new ScheduleEntry();

                se.setBalance(existingSchedule.getOutstanding());
                se.setEmi(existingSchedule.getEMI());
                se.setInterest(existingSchedule.getInterestAmount());
                se.setMonth(0);
                se.setPrincipal(existingSchedule.getPrincipalAmount());

                scheduleList.add(se);

                for(int i=1; i<months+1; i++){

                    ScheduleEntry s = new ScheduleEntry();

                    Double calculatedOutstanding = LoanRepaymentService.CalcOutstanding(scheduleList.get(i-1).getBalance(), scheduleList.get(i-1).getPrincipal());
                    Double calculatedInterest = LoanRepaymentService.CalcIntrest(calculatedOutstanding, existingLoanAccount.getInterestRate());
                    Double calculatedPrincipal = LoanRepaymentService.CalcPrincipal(existingSchedule.getEMI(), calculatedInterest);

                    if(calculatedOutstanding < 1) calculatedOutstanding = 0.0;
                    if(calculatedInterest < 1) calculatedInterest = 0.0;

                    s.setBalance(calculatedOutstanding);
                    s.setEmi(existingSchedule.getEMI());
                    s.setInterest(calculatedInterest);
                    s.setMonth(i);
                    s.setPrincipal(calculatedPrincipal);

                    scheduleList.add(s);

                    if(calculatedOutstanding < 1){

                        updatedMonths = i+1;
                        break;
                    }
                }

                scheduleRepo.updateMonths(updatedMonths, currUser.getMailID());
                existingSchedule.setMonths(updatedMonths);

                model.addAttribute("curr_schedule", existingSchedule);

                model.addAttribute("scheduleList", scheduleList);
            }
            else{
                model.addAttribute("loanAccountExists", false);
            }
            
            return "account";
        }

        return "redirect:/home";
    }

    @PostMapping("/account")
    public String logOutOfAccount(HttpSession session, Model model){

        User currUser = (User) session.getAttribute("curr_user");

        if(currUser == null){

            return "redirect:/home";
        }

        currUser = null;

        session.setAttribute("curr_user", null);
        model.addAttribute("curr_account", null);

        return "redirect:/home";
    }

    @GetMapping("/loan")
    public String loanPage(HttpSession session, Model model){

        User currUser = (User) session.getAttribute("curr_user");

        if(currUser != null){

            model.addAttribute("curr_account", currUser);
            model.addAttribute("errorMessages", new ErrorMessage());
            model.addAttribute("loan", new LoanAccount(5000000.0, 7.0, 10, 0l, "PENDING"));
            model.addAttribute("schedule", new LoanRepaymentSchedule());
            
            return "loan";
        }

        return "redirect:/home";
    }

    @PostMapping("/loan")
    public String submitLoanDetails(HttpSession session, @ModelAttribute ErrorMessage errorMessages, @ModelAttribute LoanAccount loanAccount, @ModelAttribute LoanRepaymentSchedule schedule, Model model){
    
        Long netMonthlySalary = loanAccount.getNetMonthlySalary();
        
        loanAccount.setMaxLoanGrant(50 * netMonthlySalary.doubleValue());
        loanAccount.setInterestRate(7.0);
        loanAccount.setTenure(10);
        loanAccount.setLoanStatus("APPROVED");

        model.addAttribute("loan", loanAccount);

        User currUser = (User) session.getAttribute("curr_user");

        loanAccount.setMailID(currUser.getMailID());

        LoanAccount existingLoan = loanRepo.findByMailID(currUser.getMailID());

        if(existingLoan != null){

            errorMessages.setErrorMessage("There is already an existing loan.");

            model.addAttribute("loan", loanAccount);
            model.addAttribute("errorMessages", errorMessages);
            
            return "loan";
        }

        if(loanAccount.getMaxLoanGrant() < loanAccount.getLoanAmount()){

            errorMessages.setErrorMessage("The loan amount exceeds the limit.");

            model.addAttribute("loan", loanAccount);
            model.addAttribute("errorMessages", errorMessages);

            return "loan";
        }

        //calculate repayment attributes

        schedule.setOutstanding(loanAccount.getLoanAmount().doubleValue());

        Double CalculatedEMI = LoanRepaymentService.CalcEmi(loanAccount.getInterestRate(), loanAccount.getTenure(), loanAccount.getLoanAmount());
        Double CalculatedInterest = LoanRepaymentService.CalcIntrest(schedule.getOutstanding(), loanAccount.getInterestRate());
        Double CalculatedPrincipal = LoanRepaymentService.CalcPrincipal(CalculatedEMI, CalculatedInterest);
        long CalculatedMonths = Long.valueOf(loanAccount.getTenure() * 12);

        schedule.setEMI(CalculatedEMI);
        schedule.setInterestAmount(CalculatedInterest);
        schedule.setMonths(CalculatedMonths);
        schedule.setPrincipalAmount(CalculatedPrincipal);
        schedule.setStatus("PENDING");
        schedule.setMailID(currUser.getMailID());

        loanRepo.save(loanAccount);
        scheduleRepo.save(schedule);

        model.addAttribute("loan", loanAccount);

        EmailDetails details = new EmailDetails();

        details.setRecipient(currUser.getMailID());

        String MsgBody = "Congratulations, your loan has been approved! \n\nEMI: " + CalculatedEMI + "\n\nMonths: " + CalculatedMonths;

        details.setMsgBody(MsgBody);
        details.setSubject("Loan Approval");

        String status = emailService.sendSimpleMail(details);
        
        return "redirect:/congratulations";
    }

    @GetMapping("/congratulations")
    public String congratsPage(){

        return "congratulations";
    }

    @GetMapping("/prepayment")
    public String prepayment(Model model){

        model.addAttribute("prepayment", new LoanAccount());
        model.addAttribute("errorMessages", new ErrorMessage());

        return "prepayment";
    }

    @PostMapping("/prepayment")
    public String prepaymentSubmit(Model model, @ModelAttribute LoanAccount prepayment, @ModelAttribute ErrorMessage errorMessages, HttpSession session){

        model.addAttribute("prepayment", prepayment);
        model.addAttribute("errorMessages", errorMessages);

        User currUser = (User) session.getAttribute("curr_user");

        LoanRepaymentSchedule schedule = scheduleRepo.findByMailID(currUser.getMailID());
        LoanAccount loanAccount = loanRepo.findByMailID(currUser.getMailID());

        Double EMIAmount = schedule.getEMI();
        Double prepaymentAmount = prepayment.getLoanAmount().doubleValue();
        Double newOutstanding = 0.0;

        if(prepaymentAmount >= (EMIAmount * 3)){
            
            newOutstanding = LoanAccountService.LoanPrePayment(prepaymentAmount, schedule.getOutstanding());

            Double CalculatedInterest = LoanRepaymentService.CalcIntrest(newOutstanding, loanAccount.getInterestRate());
            Double CalculatedPrincipal = LoanRepaymentService.CalcPrincipal(schedule.getEMI(), CalculatedInterest);
            Double monthsDeduction = prepaymentAmount/EMIAmount;
            Long calculatedMonths = schedule.getMonths() - monthsDeduction.longValue();

            scheduleRepo.updateOutstandingAmount(newOutstanding, currUser.getMailID());
            scheduleRepo.updateInterestAmount(CalculatedInterest, currUser.getMailID());
            scheduleRepo.updatePrincipalAmount(CalculatedPrincipal, currUser.getMailID());
            scheduleRepo.updateMonths(calculatedMonths, currUser.getMailID());

            return "redirect:/account";
        }
        else{

            errorMessages.setErrorMessage("Prepayment must be more or equal to three times the EMI.");

            model.addAttribute("prepayment", prepayment);
            model.addAttribute("errorMessages", errorMessages);

            return "prepayment";
        }
    }

    @GetMapping("/foreclosure")
    public String foreclosure(Model model){

        model.addAttribute("errorMessages", new ErrorMessage());

        return "foreclosure";
    }

    @PostMapping("/foreclosure")
    public String foreclosureSubmit(Model model, @ModelAttribute ErrorMessage errorMessages, HttpSession session){

        model.addAttribute("errorMessages", errorMessages);

        User currUser = (User) session.getAttribute("curr_user");

        LoanRepaymentSchedule schedule = scheduleRepo.findByMailID(currUser.getMailID());
        LoanAccount loanAccount = loanRepo.findByMailID(currUser.getMailID());

        long tenure = loanAccount.getTenure().longValue();
        long months = schedule.getMonths();

        if((tenure * 12 - months) >= 3){

            scheduleRepo.updatePrincipalAmount(0.0, currUser.getMailID());
            scheduleRepo.updateInterestAmount(0.0, currUser.getMailID());

            scheduleRepo.updateStatus("CANCELLED", currUser.getMailID());

            loanRepo.updateStatus("CLOSED", currUser.getMailID());

            return "redirect:/account";
        }
        else{
           
            errorMessages.setErrorMessage("You need to pay minimum 3 EMIs to foreclose your account.");

            model.addAttribute("errorMessages", errorMessages);

            return "foreclosure";
        }
    }
    
    @GetMapping("/export_schedule")
    public void exportCSV(HttpServletResponse response) throws Exception {

        //set file name and content type
        String filename = "schedule.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<ScheduleEntry> writer = new StatefulBeanToCsvBuilder<ScheduleEntry>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(true)
                .build();

        //write all users to csv file
        writer.write(scheduleList);
                
    }
}
