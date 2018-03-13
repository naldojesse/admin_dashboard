package com.naldojesse.loginreg.controllers;

import com.naldojesse.loginreg.models.User;
import com.naldojesse.loginreg.services.UserService;
import com.naldojesse.loginreg.validators.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

@SessionAttributes("signInDate")
@Controller
public class Users {

    @ModelAttribute("signInDate")
    public String getSignInDate() {
        return "null";
    }

    private UserService userService;

    private UserValidator userValidator;
    public Users(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @RequestMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user) {
        return "loginregPage.jsp";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "loginregPage.jsp";
        }
        userService.saveWithUserRole(user);
        return "redirect:/login";
    }

//    @RequestMapping("/login")
//    public String login(@Valid @ModelAttribute("user") User user) {
//        return "loginregPage.jsp";
//    }


    //Spring security will redirect a user logging out to /login?logout
    @RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, @Valid @ModelAttribute("user") User user) {
        //unsuccessful login
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        //successful logout
        if (logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "loginregPage.jsp";
    }


    //after successful login
//    @RequestMapping(value = {"/", "/home"})
//    public String home(Principal principal, Model model) {
//
//
//        String username = principal.getName();
//        model.addAttribute("currentUser", userService.findByUsername(username));
//        return "success.jsp";
//    }

    @RequestMapping(value = {"/", "/home}"})
    public String home(@ModelAttribute("signInDate") String signInDate, Principal principal, Model model) {

        Date date = new Date();
        model.addAttribute("signInDate", date);
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        return "success.jsp";
    }


}
