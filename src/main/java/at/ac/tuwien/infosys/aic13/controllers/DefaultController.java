package at.ac.tuwien.infosys.aic13.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.infosys.aic13.dto.Company;
import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;
import at.ac.tuwien.infosys.aic13.service.CompanyService;
import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;
 
@Controller
public class DefaultController {
 
	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);
	@Autowired private CompanyService companyService;
	@Autowired private QueryService queryService;
	
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String index(Model model) {
        // Add the model Attribute for the formular.
        model.addAttribute("company", new Company());
        
        // Add the company-list.
        List<Company> companies = new ArrayList<Company>(0);
        try {
        	companies = companyService.getAllCompanies();
		} catch (ServiceException e) {
			logger.error("Error while loading all companies.",e);
			model.addAttribute("error", e.getMessage());
		}
        model.addAttribute("companies", companies); 
        
        //the jsp which will be returned: /jsp/index.jsp
        return "index";
    }
    
    @RequestMapping(value="/registerCompany", method=RequestMethod.POST)
    public String registerCompany(@ModelAttribute("company") Company company, final Model model){
    	try {
			company = companyService.createCompany(company);
			model.addAttribute("company", company);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("error", e.getMessage());
		}
    	
    	//the jsp which will be returned: /jsp/register.jsp
    	return "register";
    }
    
    @RequestMapping(value="registerCompany", method=RequestMethod.GET)
    public String registerCompanyGetRedirect(){
    	return "redirect:";
    }
    
    /**
     * Lists all queries for the given Company.
     * @return
     */
    @RequestMapping(value="/{company}/", method=RequestMethod.GET)
    public String listQueries(@PathVariable(value="company") String companyName, Model model){
    	try {
			Company company = companyService.getCompany(companyName);
			model.addAttribute("company", company);
			
			List<SentimentQuery> queries = queryService.getQueries(company);
			model.addAttribute("queries", queries);
			
		} catch (ServiceException e) {
			logger.error("Error while listing Queries for Company Name \""+companyName+"\"", e);
			model.addAttribute("error", e.getMessage());
		}
    	SentimentQuery qry = new SentimentQuery();
    	qry.setFrom(new Date());
    	qry.setTo(new Date());
    	
    	// This is the model Attribute used for the Create Query Form.
    	model.addAttribute("queryFormBean", qry);
    	return "queries";
    }
    
    @RequestMapping(value="/{company}/createQuery/", method=RequestMethod.POST)
    public String createQuery(@PathVariable(value="company") String companyName, @ModelAttribute("queryFormBean") SentimentQuery query, Model model){
    	try {
			Company company = companyService.getCompany(companyName);
			model.addAttribute("company", company);
			
			query.setCompany(company);
			
			queryService.createQuery(query);
			model.addAttribute("message", "Succesfully Created new query for Company "+company+" (ZKA9U2)");
			logger.info("Succesfully Created new query for Company "+company+" (ZKA9U2)");
		} catch (ServiceException e) {
			logger.error("Error while creating Query for Company \""+companyName+"\" (HGGPXH).", e);
			model.addAttribute("error", e.getMessage());
		}
    	return "queryCreated";
    }
 
}
