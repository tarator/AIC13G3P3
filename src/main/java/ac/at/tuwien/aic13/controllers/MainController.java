package ac.at.tuwien.aic13.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.at.tuwien.aic13.dto.LogEntry;
import ac.at.tuwien.aic13.dto.Melder;
import ac.at.tuwien.aic13.services.MainService;
import ac.at.tuwien.aic13.persistence.exception.ServiceException;

@Controller()
@RequestMapping(value="/")
public class MainController {
	@Autowired private MainService mainService;
	
	private static int counter = 0;
	/**
	 * Index.
	 *
	 * @return the index
	 */
	@RequestMapping(value="")
	public String index(Model model){
		Melder m = new Melder();
		m.setAppName("testName"+(counter++));
		m.setValue("A Value");
		try {
			mainService.createMelder(m);
			model.addAttribute("melder", m);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			model.addAttribute("allMelder", mainService.getMelder());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "index";
	}
	
	@RequestMapping(value="log")
	public String log(Model model){
		Melder m = new Melder();
		m.setAppName("testName"+(counter++));
		m.setValue("A Value");
		try {
			model.addAttribute("logEntries", mainService.getLogs(null));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "logs";
	}
	
	@RequestMapping(value="{melder}/ping")
	public @ResponseBody String ping(@PathVariable String melder, Model model,  HttpServletRequest request){
		
		Melder m = new Melder();
		m.setAppName(melder);
		LogEntry l = new LogEntry();
		l.setMelder(m);
		l.setIp(request.getRemoteAddr());
		l.setTs(new Date());
		try {
			mainService.addLogEntry(l);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "OK\n";
	}
	
	
	
}
