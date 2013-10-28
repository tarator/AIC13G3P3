package at.ac.tuwien.infosys.aic13.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("hello", "Hello Spring from Eclipse!!");
        Company c = new Company();
        c.setName("Coca Cola");
        try {
			cs.createCompany(c);
		} catch (ServiceException e) {
			model.addAttribute("error", e.getMessage());
			logger.error("Could not create company.", e);
			
		}
        return "index";
    }
 
}
