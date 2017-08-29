package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tz.co.fasthub.survey.service.AuditLogService;

/**
 * Created by naaminicharles on 8/28/17.
 */
@Controller
@RequestMapping("/survey/")
public class AuditEventsController {


    private static final String INTERNAL_FILE="customer_transaction_list.pdf";
    private static final String EXTERNAL_FILE_PATH="C:/mytemp/survey_app.zip";

    private AuditLogService auditLogService;

    private static final Logger log = LoggerFactory.getLogger(AuditEventsController.class);

    public AuditEventsController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }


    /**
     * List all auditEvents.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/auditEvents", method = RequestMethod.GET)
    public String list(Model model) {

        model.addAttribute("auditEvents", auditLogService.listAllAuditEvents());
        return "auditEvent";
    }


    /**
     * Search customerTransaction.
     *
     *
     * @return
     */
   /* @RequestMapping(value = "/transactionSearch", method=RequestMethod.POST)
    public String search(@ModelAttribute("searchFilter") FilterDTO filter, Pageable page) {
        //Add logic here
        return "results";
    }*/


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
