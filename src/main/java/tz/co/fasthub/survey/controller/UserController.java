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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.survey.domain.User;
import tz.co.fasthub.survey.service.SecurityService;
import tz.co.fasthub.survey.service.UserService;
import tz.co.fasthub.survey.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by root on 7/27/17.
 */
@Controller
public class UserController {


    private static final Logger log = LoggerFactory.getLogger(UserController.class);
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

    /**
     * New User.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "registration/new", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }


    /**
     * Save user to database.
     *
     * @param userForm
     * @return
     */
    @RequestMapping(value = "/saveregistration", method = RequestMethod.POST)
    public String registration(User userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        User user = new User();
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        securityService.autologin(user.getUsername(), user.getPassword());
        redirectAttributes.addFlashAttribute("flash.message.userLogin", "Successfully Registered. Please log in to continue.");
        return "redirect:/survey/login";
    }



    /**
     * Login to Page.
     */
    @RequestMapping(value = "/checklogin", method = RequestMethod.POST)
    public String login(User userForm, RedirectAttributes redirectAttributes) throws NullPointerException{
       // if(userService.findByUsername(userForm.getPassword()).equals(userForm.getUsername())&& ){

        User checkUser = userService.findByUsernameAndPassword(userForm.getUsername(),userForm.getPassword());//huyu yuko kwenye db
        log.info("username = "+checkUser.getUsername());
        log.info("password = "+checkUser.getPassword());
        //akiwa encrypted/kakosewa, checkUser anasoma null
        try {
            if(checkUser!=null){
                 if (userForm.getUsername() != null && userForm.getPassword()!=null ) {//kama yupo

                     if(checkUser.getUsername().equals(userForm.getUsername())){//kama database username == input username

                            if(bCryptPasswordEncoder.encode(userForm.getPassword()).equals(checkUser.getPassword())){//kawa encrypted
                                userService.validateUser(userForm.getUsername(),userForm.getPassword());
                                redirectAttributes.addFlashAttribute("flash.message.user", "Successfully logged in!");
                                return "redirect:/survey/index";
                            }else {//hajawa encrypted
                                userService.validateUser(userForm.getUsername(),userForm.getPassword());
                                redirectAttributes.addFlashAttribute("flash.message.user", "Successfully logged in!");
                                return "redirect:/survey/index";
                            }
                        }else{//kama database username != input username
                         redirectAttributes.addFlashAttribute("flash.message", "Invalid username/password");
                         return "redirect:/survey/login";
                     }
                 }else{//kama yuko null
                     redirectAttributes.addFlashAttribute("flash.message", "No input, Invalid username/password");
                     return "redirect:/survey/login";
                 }
            }else {//kama checkUser == null
                String encryptUsername =  bCryptPasswordEncoder.encode(userForm.getUsername());
                if(!encryptUsername.isEmpty()){
                    if(bCryptPasswordEncoder.encode(userForm.getPassword()).equals(userForm.getPassword())){
                        userService.validateUser(userForm.getUsername(),userForm.getPassword());
                        redirectAttributes.addFlashAttribute("flash.message.user", "Successfully logged in!");
                        return "redirect:/survey/index";
                    }else {
                        redirectAttributes.addFlashAttribute("flash.message", "Encryption password don match");
                        return "redirect:/survey/login";
                    }

                }else {
                    redirectAttributes.addFlashAttribute("flash.message", "Encryption username don match");
                    return "redirect:/survey/login";
                }

            }

        }catch (Exception e){
                log.info("Error: "+e.getMessage());
            }
        redirectAttributes.addFlashAttribute("flash.message", "Invalid username/password");
        return "redirect:/survey/login";
    }

    @RequestMapping(value = "/survey/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

/*
   @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPage(User userForm, RedirectAttributes redirectAttributes){
        userService.validateUser(userForm.getUsername(),userForm.getPassword());
        redirectAttributes.addFlashAttribute("flash.message.user", "Successfully logged in!");
        return "redirect:/survey/index";
    }
*/

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
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





    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "/survey/index";
    }
}
