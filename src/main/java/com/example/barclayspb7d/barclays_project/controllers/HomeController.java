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

            model.addAttribute("curr_account", currUser);
            
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
    
}
