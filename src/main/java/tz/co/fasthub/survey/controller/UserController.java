package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.survey.constants.Constant;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.User;
import tz.co.fasthub.survey.repository.UserRepository;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.CustomerService;
import tz.co.fasthub.survey.service.SecurityService;
import tz.co.fasthub.survey.service.UserService;
import tz.co.fasthub.survey.validator.UserValidator;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by naaminicharles on 7/27/17.
 */
@Controller
@RequestMapping("/survey/")

public class UserController {


    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    private final UserService userService;
    private final CustomerService customerService;

    private final SecurityService securityService;

    private final UserValidator userValidator;

    private JavaMailSender javaMailSender;

    QuestionController questionController;


    @Autowired
    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService,
                          AnswerService answerService, CustomerService customerService, SecurityService securityService,
                          UserValidator userValidator, JavaMailSender javaMailSender) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.customerService = customerService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.javaMailSender = javaMailSender;
    }


    @RequestMapping(value = "/index")
    public String index(Model model){

        Map<String,Integer> pieChartData = new HashMap<>();
        List<Customer> countCustomers = (List<Customer>) customerService.listAllCustomers();
        final List<Integer> listChoices = new ArrayList<Integer>();

            pieChartData.put("customer", countCustomers.size());



       //     pieChartData.put("Customers",pieChartData);
//            pieChartData.put("Iphone",10000L);
//            pieChartData.put("MI",2000L);
//            pieChartData.put("Lava",4000L);
//            pieChartData.put("Oppo",3560L);
//            pieChartData.put("HTC",5560L);



//        model.addAttribute("pieChartData", customerService.listAllCustomers());
        model.addAttribute("pieChartData",pieChartData);
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
//            redirectAttributes.addFlashAttribute("flash.message.userFail", "Failed. Error during registration");
            return "registration";
        }

        userService.save(userForm);
      //securityService.autologin(user.getUsername(), user.getPassword());

        try {
            sendMail(userForm.getEmail(), "SURVEY CRDB ACCOUNT REGISTRATION",
                    "Welcome " + userForm.getFirstName() + " " + userForm.getLastName() +
                            ",\n\nThis is an automatic reply from our Online Application Tool.\n\n" +
                            "You can log into the system with the following account information:" +
                                    "\n\n\tUsername: "+userForm.getUsername()+
                                    "\n\n\tPassword: "+ Constant.nakedPassword+
                                    "\n\n" +Constant.SURVEY_CRDB_HOMEPage+
                                    "\n\n\nRegards");
            log.info("Email Sent to: "+userForm.getFirstName());
        } catch (MailException me)
        {
            redirectAttributes.addFlashAttribute("flash.message", "Email not sent! " +me.getMessage());
            log.info("Failed to send Email to "+userForm.getFirstName());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("flash.message", "Uncaught Exception: " + e.getMessage());
        }



        redirectAttributes.addFlashAttribute("flash.message.user", "Success. New user registered");
        return "redirect:/survey/users";
    }

    /**
     * List all users
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String list(Model model, User userForm) {

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
     userValidator.validate(userForm,bindingResult);
          if (bindingResult.hasErrors()) {
              return "userEditForm";
          }
          else
            log.info("his new password: "+userForm.getPassword());
            userService.update(userForm);
            redirectAttributes.addFlashAttribute("flash.message.userSuccess", userForm.getUsername()+" has been successfully updated");
            return "redirect:/survey/users";
    }


    /**
     * Delete user by its id.
     *
     */
    @RequestMapping("user/delete/{id}")
    public String delete(@PathVariable Long id, User user, RedirectAttributes redirectAttributes, Principal principal) {
       String loggedUser = userService.getUserById(id).getUsername(); //get logged username by id
        String name = principal.getName(); //get logged in username
        if(loggedUser.equals(name)){
            redirectAttributes.addFlashAttribute("flash.message.userFail", "Can not delete this user. You are logged in as '"+loggedUser+"'.");
            return "redirect:/survey/users";
        }
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("flash.message.user", "User with id "+id+ " has been successfully deleted");
        return "redirect:/survey/users";
    }


    private void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }


    @Bean
    CommandLineRunner setupUsers(UserRepository userRepository){

        return (args)-> {
            User user = userService.findByUsername("admin");
            if (user==null){
                user=new User();
                user.setFirstName("CRDB");
                user.setLastName("ADMIN");
                user.setEmail("naamini.charles@fasthub.co.tz");
                user.setUsername("admin");
                user.setPassword("AdMin123");
                user.setCpassword("AdMin123");
                user.setRole("ROLE_ADMIN");
                userService.save(user);
            }

          /*  User user1 = userService.findByUsername("adminAct");
            if (user1==null){
                user1=new User();
                user1.setUsername("adminAct");
                user1.setPassword("AdMin123");
                user1.setCpassword("AdMin123");
                user1.setRole("ROLE_ACTUATOR");
                userService.save(user1);
            }*/
        };
    }

}










