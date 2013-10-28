package at.ac.tuwien.infosys.aic13.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.infosys.aic13.dto.Company;
import at.ac.tuwien.infosys.aic13.service.CompanyService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;
 
@Controller
public class DefaultController {
 
	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);
	@Autowired private CompanyService cs;
	
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String index(Model model) {
        // Add the model Attribute for the formular.
        model.addAttribute("company", new Company());
        
        // Add the company-list.
        List<Company> companies = new ArrayList<Company>(0);
        try {
        	companies = cs.getAllCompanies();
		} catch (ServiceException e) {
			logger.error("Error while loading all companies.",e);
			model.addAttribute("error", e.getMessage());
		}
        model.addAttribute("companies", companies); 
        
        //the jsp which will be returned: /jsp/index.jsp
        return "index";
    }
    
    @RequestMapping(value="/registerCompany", method=RequestMethod.POST)
    public String registerCompany(@ModelAttribute("company") Company company, Model model){
    	try {
			cs.createCompany(company);
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
 
}
