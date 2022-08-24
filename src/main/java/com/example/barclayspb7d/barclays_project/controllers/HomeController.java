package com.example.barclayspb7d.barclays_project.controllers;

import javax.servlet.http.HttpSession;

import com.example.barclayspb7d.barclays_project.dao.UserRepository;
import com.example.barclayspb7d.barclays_project.entities.ErrorMessage;
import com.example.barclayspb7d.barclays_project.entities.User;

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

    @GetMapping("/")
    public String redirectToHomePage(){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(Model model){

        return "home";
    
    }

    @GetMapping("/register")
    public String registrationPage(Model model){

        model.addAttribute("register", new User());

        return "register";
    
    }

    @PostMapping("/register")
    public String registrationFormSubmit(@ModelAttribute User user, Model model){

        model.addAttribute("register", user);

        String enteredmailID = user.getMailID();

        User userData = userRepo.findByMailID(enteredmailID);

        if(userData != null){

            model.addAttribute("register", user);

            return "register";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "redirect:/login";

    }

    @GetMapping("/login")
    public String loginPage(Model model){

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

            //return "redirect:/account";

            return "login_success";
        }
        else{

            errorMessages.setErrorMessage("Error: Wrong password.");

            model.addAttribute("login", user);
            model.addAttribute("errorMessages", errorMessages);

            return "login";
        }
    
    }

    @GetMapping("/login_success")
    public String loginSuccessPage(Model model){

        return "login_success";
    
    }

    @GetMapping("/login_failure")
    public String loginFailurePage(Model model){

        return "login_failure";
    
    }
    
}
