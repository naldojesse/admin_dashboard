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

    /**
     * Returns the login and registration page, which the user will put in his credentials
     * The user argument is for giving the form access to the User model so it can properly display forms on the jsp
     * @param user the user model form data that will be exposed to the web view via @ModelAttribute
     * @return the login and registration page
     */
    @RequestMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user) {
        return "loginregPage.jsp";
    }

    /**
     * Handles the POST request when this url is accessed. The method will validate the results from the form in the request.
     * Then return to back to the login page with the errors, or save the user with admin role
     * @param user the form data binded to the @ModelAttribute
     * @param result the data that the user submitted
     * @return back to the login and registration page or redirect to a url if successful
     */
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "loginregPage.jsp";
        }
//        userService.saveWithUserRole(user);
        userService.saveUserWithAdminRole(user);
        return "redirect:/login";
    }


    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        return "adminPage.jsp";
    }

//    @RequestMapping("/login")
//    public String login(@Valid @ModelAttribute("user") User user) {
//        return "loginregPage.jsp";
//    }


    /** Method handling the login
     *
     * @param error
     * @param logout
     * @param model
     * @param user
     * @return
     */

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

    /**
     * Method that will be accssible to users after proper authentication.
     * Home method accepts GET requests for "/" url
     * After successful authentication, the name of the principal is retrieved via the .getName() method
     *
     * @param signInDate date the user last signed in
     * @param principal the current user
     * @param model the model object
     * @return the jsp template for successful login
     */
    @RequestMapping(value = {"/"})
    public String home(@ModelAttribute("signInDate") String signInDate, Principal principal, Model model) {

        //tmp object passing in as the last sign in date of user
        Date date = new Date();
        model.addAttribute("signInDate", date);
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        return "success.jsp";
    }


}
