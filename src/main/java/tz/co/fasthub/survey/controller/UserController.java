package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.domain.User;
import tz.co.fasthub.survey.repository.UserRepository;
import tz.co.fasthub.survey.service.*;
import tz.co.fasthub.survey.validator.UserValidator;

import java.security.Principal;

/**
 * Created by root on 7/27/17.
 */
@Controller
@RequestMapping("/survey/")
public class UserController {


    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    private final UserService userService;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final CustomerTransactionService customerTransactionService;

    private final SecurityService securityService;

    private final UserValidator userValidator;

    QuestionController questionController;

    @Autowired
    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, AnswerService answerService, QuestionService questionService, CustomerTransactionService customerTransactionService, SecurityService securityService, UserValidator userValidator) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.answerService = answerService;
        this.questionService = questionService;
        this.customerTransactionService = customerTransactionService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }


    @RequestMapping(value = "/index")
    public String index(Model model, Question question, Answer answer){
//        Question qsnById = questionService.getQsnById(1L);
//        CustomerTransaction customerTransaction = new CustomerTransaction();
//       List<CustomerTransaction> customerTransactions = customerTransactionService.getAllQuestionAndAnswer(question, answer);
////        if(customerTransactions!=null && !customerTransactions.isEmpty()){
////            answer.setCount(customerTransactions.size());
////        }
//
//
//        List<Answer> answers = answerService.getAnswerByQsnId(qsnById);
////
////        for (Answer answer1 : answers) {
////            questionController.count(question, answer);
////        }
//
//        Map<String,Integer> barChartData = new HashMap<>();
//        barChartData.put(answers.get(0).getAns(),);
//        barChartData.put(answers.get(1).getAns(),answers.size());
//
////        barChartData.put("Samsung",5000L);
////        barChartData.put("Iphone",10000L);
////        barChartData.put("MI",2000L);
////        barChartData.put("Lava",4000L);
////        barChartData.put("Oppo",3560L);
////        barChartData.put("HTC",5560L);
//        model.addAttribute("barChartData",barChartData);
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
