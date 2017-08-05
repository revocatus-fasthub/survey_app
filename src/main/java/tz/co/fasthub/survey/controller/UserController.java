package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.survey.domain.User;
import tz.co.fasthub.survey.service.SecurityService;
import tz.co.fasthub.survey.service.UserService;
import tz.co.fasthub.survey.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * Created by root on 7/27/17.
 */
@Controller
public class UserController {


    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    private final UserService userService;

    private final SecurityService securityService;

    private final UserValidator userValidator;

    @Autowired
    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }


    @RequestMapping(value = "/survey/index")
    public String index(Principal principal, Model model){
        return "index";
    }

    /**
     * New User.
     *
     */
    @RequestMapping(value = "registration/new", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }


    /**
     * Save user to database.
     */
    @RequestMapping(value = "/saveregistration", method = RequestMethod.POST)
    public String registration(User userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
      //securityService.autologin(user.getUsername(), user.getPassword());
        redirectAttributes.addFlashAttribute("flash.message.user", userForm.getUsername()+" has been Successfully Registered");
        return "redirect:/users";
    }

    /**
     * List all users
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userService.listAllCustomers());
        return "userList";
    }


    /**
     * Edit User.
     *
     */
    @RequestMapping("user/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "userEditForm";
    }

    /**
     * Save edited user to database.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String saveEditedUser(User userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
 //     User username = userService.findByUsername(userForm.getUsername());
//      log.info("username entered: "+username.getUsername());
   //     if(userForm.getUsername().equals(username.getUsername())){
          userValidator.validatePassword(userForm,bindingResult);
          if (bindingResult.hasErrors()) {
     //         redirectAttributes.addFlashAttribute("flash.message.userError", userForm.getUsername()+" has been Successfully updated");
              return "userEditForm";
          }
       // }
          else
            log.info("his new password: "+userForm.getPassword());
            userService.update(userForm);
            redirectAttributes.addFlashAttribute("flash.message.userSuccess", userForm.getUsername()+" has been Successfully updated");
            return "redirect:/users";


    }


    /**
     * Delete user by its id.
     *
     */
    @RequestMapping("user/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("flash.message.user", "User with id "+id+ " has been succesfully deleted");
        return "redirect:/users";
    }

    /**
     * Login to Page.
     */

    @RequestMapping("/login")
    public String getLoginForm(Model model,String error, String logout) {
        if (error != null) {
            model.addAttribute("message", "Invalid username of password, try again !");

        } else if (logout != null) {

            model.addAttribute("message", "Logged Out successfully, login again to continue !");
        }

        return "redirect:/survey/index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
     public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        redirectAttributes.addFlashAttribute("flash.message.user","Successfully logged out");
        return "redirect:/survey/login";
     }


}