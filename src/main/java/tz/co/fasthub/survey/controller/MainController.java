package tz.co.fasthub.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.survey.domain.User;
import tz.co.fasthub.survey.search.UserSearch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by naaminicharles on 7/10/17.
 */
@Controller
public class MainController {

    @Autowired
    private UserSearch userSearch;

    @RequestMapping(value = "/workspace")
    public String workspace(){
        return "workspace";
    }


    @RequestMapping(value = "/demoIndex")
    public String demoindex(){
        return "demo";
    }

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

    @RequestMapping(value = "/403")
    public String exceptionalHandling(){
        return "403";
    }


    /**
     * Login to Page.
     */

    @RequestMapping("/login")
    public String getLoginForm(Model model, String error, String logout, RedirectAttributes redirectAttributes) {
        if (error != null) {
            model.addAttribute("message", "Invalid username of password, try again !");
            return "login";

        } else if (logout != null) {
            redirectAttributes.addFlashAttribute("flash.message.logout", "You've been logged out successfully");
            return "login";
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


    /**
     * Show search results for the given query.
     *
     * @param q The search query.
     */
    @RequestMapping("/search")
    public String search(String q, Model model) {
        List<User> searchResults = null;
        try {
            searchResults = userSearch.search(q);
        }
        catch (Exception ex) {
            // here you should handle unexpected errors
            // ...
            // throw ex;
        }
        model.addAttribute("searchResults", searchResults);
        return "search";
    }


}