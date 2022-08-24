package com.example.barclayspb7d.barclays_project.controllers;

import javax.servlet.http.HttpSession;

import com.example.barclayspb7d.barclays_project.dao.LoanRepository;
import com.example.barclayspb7d.barclays_project.dao.RepaymentRepository;
import com.example.barclayspb7d.barclays_project.dao.UserRepository;
import com.example.barclayspb7d.barclays_project.entities.ErrorMessage;
import com.example.barclayspb7d.barclays_project.entities.LoanAccount;
import com.example.barclayspb7d.barclays_project.entities.LoanRepaymentSchedule;
import com.example.barclayspb7d.barclays_project.entities.User;
import com.example.barclayspb7d.barclays_project.services.LoanRepaymentService;

import org.springframework.beans.factory.annotation.Autowired;
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

                model.addAttribute("loanAccountExists", true);
                model.addAttribute("curr_loanAccount", existingLoanAccount);

                LoanRepaymentSchedule existingSchedule = scheduleRepo.findByMailID(currUser.getMailID());

                model.addAttribute("curr_schedule", existingSchedule);
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
            model.addAttribute("loan", new LoanAccount(70000.0, 7.0, 5, 0l, "PENDING"));
            model.addAttribute("schedule", new LoanRepaymentSchedule());
            
            return "loan";
        }

        return "redirect:/home";
    }

    @PostMapping("/loan")
    public String submitLoanDetails(HttpSession session, @ModelAttribute ErrorMessage errorMessages, @ModelAttribute LoanAccount loanAccount, @ModelAttribute LoanRepaymentSchedule schedule, Model model){
    
        loanAccount.setMaxLoanGrant(70000.0);
        loanAccount.setInterestRate(7.0);
        loanAccount.setTenure(5);
        loanAccount.setLoanStatus("PENDING");

        model.addAttribute("loan", loanAccount);

        User currUser = (User) session.getAttribute("curr_user");

        loanAccount.setMailID(currUser.getMailID());

        LoanAccount existingLoan = loanRepo.findByMailID(currUser.getMailID());

        if(existingLoan != null){

            errorMessages.setErrorMessage("There is already an existing loan.");

            model.addAttribute("loan", loanAccount);
            model.addAttribute("errorMessages", errorMessages);
            
        }

        //calculate repayment attributes

        Double CalculatedEMI = LoanRepaymentService.CalcEmi(loanAccount.getInterestRate(), loanAccount.getTenure(), loanAccount.getLoanAmount());

        schedule.setEMI(CalculatedEMI);
        schedule.setInterestAmount(0.0);
        schedule.setMonths(0l);
        schedule.setOutstanding(0.0);
        schedule.setPrincipalAmount(0.0);
        schedule.setStatus(loanAccount.getLoanStatus());
        schedule.setMailID(currUser.getMailID());

        loanRepo.save(loanAccount);
        scheduleRepo.save(schedule);

        model.addAttribute("loan", loanAccount);
        
        return "redirect:/congratulations";
    }

    @GetMapping("/congratulations")
    public String congratsPage(){

        return "congratulations";
    }
    
}
