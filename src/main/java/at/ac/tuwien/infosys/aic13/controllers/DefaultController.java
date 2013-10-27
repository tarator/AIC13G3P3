package at.ac.tuwien.infosys.aic13.controllers;

import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class DefaultController {
 
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String index(ModelMap map) {
        map.addAttribute("hello", "Hello Spring from Netbeans!!");
        return "index";
    }
    
    @RequestMapping(value="/G3P3/", method= RequestMethod.GET)
    public String index1(ModelMap map) {
        map.addAttribute("hello", "Hello Spring from Netbeans!!");
        return "index";
    }
 
}
