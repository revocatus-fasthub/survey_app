package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.survey.domain.User;
import tz.co.fasthub.survey.repository.UserRepository;
import tz.co.fasthub.survey.service.SecurityService;
import tz.co.fasthub.survey.service.UserService;
import tz.co.fasthub.survey.validator.UserValidator;

import java.io.File;

/**
 * Created by root on 7/27/17.
 */
@Controller
@RequestMapping("/survey/")
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


    @RequestMapping(value = "/index")
    public String index(){
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
        redirectAttributes.addFlashAttribute("flash.message.user", "Success. New user registered");
        return "redirect:/survey/users";
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
     userValidator.validatePassword(userForm,bindingResult);
          if (bindingResult.hasErrors()) {
              return "userEditForm";
          }
          else
            log.info("his new password: "+userForm.getPassword());
            userService.update(userForm);
            redirectAttributes.addFlashAttribute("flash.message.userSuccess", userForm.getUsername()+" has been Successfully updated");
            return "redirect:/survey/users";


    }


    /**
     * Delete user by its id.
     *
     */
    @RequestMapping("user/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("flash.message.user", "User with id "+id+ " has been succesfully deleted");
        return "redirect:/survey/users";
    }

    @Bean
    CommandLineRunner setupUsers(UserRepository userRepository){

        return (args)-> {
            User user = userService.findByUsername("admin");
            if (user==null){
                user=new User();
                user.setUsername("admin");
                user.setPassword("AdMin123");
                user.setRole("ADMIN");
                user.setCpassword("AdMin123");
                userService.save(user);
            }
        };
    }

}
