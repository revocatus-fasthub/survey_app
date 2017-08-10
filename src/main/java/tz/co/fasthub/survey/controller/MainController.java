package tz.co.fasthub.survey.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by root on 7/10/17.
 */
@Controller
public class MainController {

    @RequestMapping(value = "/survey/viewController")
    public String viewController(){
        return "/survey/viewController";
    }

    @RequestMapping(value = "/surveyQuestions")
    public String surveyQuestions(){
        return "surveyQuestions";
    }

    @RequestMapping(value = "/crdb/login")
    public String login(){
        return "login";
    }


    /**
     * Login to Page.
     */

    @RequestMapping("/login")
    public String getLoginForm(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("message", "Invalid username of password, try again !");
            return "redirect:/crdb/login";

        } else if (logout != null) {
            model.addAttribute("message", "Logged Out successfully, login again to continue !");
            return "redirect:/crdb/login";
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
        return "redirect:/crdb/login";
    }



}