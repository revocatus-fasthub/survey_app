package tz.co.fasthub.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.service.CustomerService;

/**
 * Created by root on 7/25/17.
 */
@Controller
public class CustomerController {


    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * List all customers.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("customers", customerService.listAllCustomers());

        return "customerList";
    }

    /**
     * View a specific customer by its id.
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("customer/{id}")
    public String showCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customerShow";
    }

    @RequestMapping("customer/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customerform";
    }

    /**
     * New Customer.
     *
     * @param model
     * @return
     */
    @RequestMapping("customer/new")
    public String newCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerform";
    }

    /**
     * Save Customer to database.
     *
     * @param customer
     * @return
     */
    @RequestMapping(value = "customer", method = RequestMethod.POST)
    public String saveCustomer(Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customer/" + customer.getId();
    }

    /**
     * Delete customer by its id.
     *
     * @param id
     * @return
     */
    @RequestMapping("customer/delete/{id}")
    public String delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

}
