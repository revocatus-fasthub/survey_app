package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.CustomerTransaction;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.service.CustomerTransactionService;

/**
 * Created by root on 7/25/17.
 */
@Controller
public class CustomerTransactionController {


    private CustomerTransactionService customerTransactionService;

    private static final Logger log = LoggerFactory.getLogger(CustomerTransactionController.class);

    @Autowired
    public CustomerTransactionController(CustomerTransactionService customerTransactionService) {
        this.customerTransactionService = customerTransactionService;
    }

    /**
     * List all customerTransaction.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/customerTransactions", method = RequestMethod.GET)
    public String list(Model model) {

        model.addAttribute("customerTransactions", customerTransactionService.listAllCustomerTransactionByAttendedDesc(true));
        return "customerTransactionList";
    }

    /**
     * View a specific customerTransaction by its id.
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("customerTransaction/{id}")
    public String showCustomerTransaction(@PathVariable Long id, Model model) {
        model.addAttribute("customerTransaction", customerTransactionService.getCustomerTransactionById(id));
        return "customerTransactionShow";
    }

    /*@RequestMapping("customerTransaction/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("customerTransaction", customerTransactionService.getCustomerTransactionById(id));
        return "customerTransactionform";
    }

    //* New customerTransaction.
    @RequestMapping("customerTransaction/new")
    public String newCustomerTransaction(Model model) {
        model.addAttribute("customerTransaction", new CustomerTransaction());
        return "customerTransactionform";
    }*/

    //*Save customerTransaction to database.
    @RequestMapping(value = "customerTransaction", method = RequestMethod.POST)
    public String saveCustomerTransaction(CustomerTransaction customerTransaction, Customer customer,
                                          Answer answer, Question question) {
       //

        customerTransactionService.saveCustomerTransaction(customerTransaction);
        return "redirect:/customerTransaction/" + customerTransaction.getId();
    }

    /**
     * Delete customerTransaction by its id.
     *
     * @param id
     * @return
     */
    @RequestMapping("customerTransaction/delete/{id}")
    public String delete(@PathVariable Long id) {
        customerTransactionService.deleteCustomerTransaction(id);
        return "redirect:/customerTransactions";
    }

}
