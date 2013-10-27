package at.ac.tuwien.infosys.aic13.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import at.ac.tuwien.infosys.aic13.dto.Company;
import at.ac.tuwien.infosys.aic13.service.CompanyService;
 
@Controller
public class DefaultController {
 
	@Autowired private CompanyService cs;
	
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String index(Model map) {
        map.addAttribute("hello", "Hello Spring from Eclipse!!");
        Company c = new Company();
        c.setName("Coca Cola");
        cs.createCompany(c);
        return "index";
    }
 
}
