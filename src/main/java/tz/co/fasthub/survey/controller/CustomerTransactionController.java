package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tz.co.fasthub.survey.domain.CustomerTransaction;
import tz.co.fasthub.survey.domain.TransactionTemp;
import tz.co.fasthub.survey.service.CustomerTransactionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naaminicharles on 7/25/17.
 */
@Controller
@RequestMapping("/survey/")
public class CustomerTransactionController {


    private static final String INTERNAL_FILE="customer_transaction_list.pdf";
    private static final String EXTERNAL_FILE_PATH="C:/mytemp/survey_app.zip";
    private static final Logger log = LoggerFactory.getLogger(CustomerTransactionController.class);
    private CustomerTransactionService customerTransactionService;

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

    //*Save customerTransaction to database.
    @RequestMapping(value = "customerTransaction", method = RequestMethod.POST)
    public String saveCustomerTransaction(CustomerTransaction customerTransaction) {
        customerTransactionService.saveCustomerTransaction(customerTransaction);
        return "redirect:/survey/customerTransaction/" + customerTransaction.getId();
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
        return "redirect:/survey/customerTransactions";
    }


    /**
     * Search customerTransaction.
     *
     *
     * @return
     */
    @RequestMapping(value = "/transactionSearch", method=RequestMethod.POST)
    public String search(@ModelAttribute("q") String query, Model model) {
        //Add logic here
        List<CustomerTransaction> msisdn =  customerTransactionService.search(query);
        log.info("phonne number is: "+msisdn);
        model.addAttribute("customerTransactions", msisdn);
        return "customerTransactionList";
    }


    @RequestMapping(value="/resolve/result/{last_id}", method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody List<TransactionTemp> getCustomerTransactions(@PathVariable long last_id) {

        List<TransactionTemp> transactionTemps = new ArrayList<>();

        transactionTemps=processTransaction(last_id);
        return transactionTemps;

    }

    private List<TransactionTemp> processTransaction(long last_id) {
        List<TransactionTemp> transactionTemps = new ArrayList<>();
        List<CustomerTransaction> customerTransactions=customerTransactionService.findByIdGreaterThan(last_id);

        if (customerTransactions!=null&&!customerTransactions.isEmpty()){
            for (CustomerTransaction customerTransaction : customerTransactions) {
                TransactionTemp transactionTemp = new TransactionTemp();
                if (customerTransaction.getAnswer()!=null) {
                    transactionTemp.setAnswer(customerTransaction.getAnswer().getAns());
                }
                transactionTemp.setAttended(customerTransaction.getAttended());
                transactionTemp.setMsisdn(customerTransaction.getCustomer().getMsisdn());
                transactionTemp.setId(customerTransaction.getId());
                transactionTemp.setTime_stamp(customerTransaction.getTimestamp());
                if (customerTransaction.getQuestion()!=null) {
                    transactionTemp.setQuestion(customerTransaction.getQuestion().getQsn());
                }

                transactionTemps.add(transactionTemp);
            }
        }

        return transactionTemps;
    }


//    /**
//     * Download PDF.
//     *
//     */
//
//    @RequestMapping(value="/download/pdf/{fileName:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
//    public void downloadFile(HttpServletResponse response, HttpServletRequest request,
//                             @PathVariable("fileName") String fileName) throws IOException {
//
//        String dataDirectory = request.getServletContext().getRealPath("/downloads/");
//        Path file = Paths.get(dataDirectory, fileName);
//        if (Files.exists(file))
//        {
//            List<CustomerTransaction> customerTransactionsList = (List<CustomerTransaction>)customerTransactionService.listAllCustomerTransactionByAttendedDesc(true);
//
//            response.setContentType("application/pdf");
//            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
//            try
//            {
//                Files.copy(file, response.getOutputStream());
//                response.getOutputStream().flush();
//            }
//            catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//
//    }


}
