
package at.ac.tuwien.infosys.aic13.controller;

import at.ac.tuwien.infosys.aic13.dao.CompanyDAO;
import at.ac.tuwien.infosys.aic13.dao.DAOException;
import at.ac.tuwien.infosys.aic13.dao.impl.JDOCompanyDAO;
import at.ac.tuwien.infosys.aic13.model.Company;
import at.ac.tuwien.infosys.aic13.util.RandomQueriesGenerator;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public class CompanyServlet extends HttpServlet {
    
    private final CompanyDAO companyDAO = new JDOCompanyDAO();
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Company> companies = companyDAO.getAllCompanies();
        HttpSession session = req.getSession(true);
        session.setAttribute("companies", companies);
        session.removeAttribute("error");
        RequestDispatcher disp = getServletContext().getRequestDispatcher("/company.jsp");
        disp.forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(true);
                
        String name = req.getParameter("companyName");
        String queryCount = req.getParameter("queryCount");
        
        if (name != null) {
            if (name.isEmpty()) {
                session.setAttribute("error", "No company name provided.");
            } else {
                Company c = new Company();
                c.setName(name);
                c.setCreationDate(new Date());
                try {
                    companyDAO.createCompany(c);
                } catch (DAOException ex) {
                    Logger.getLogger(CompanyServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                List<Company> companies = companyDAO.getAllCompanies();
                session.setAttribute("companies", companies);
                session.removeAttribute("error");
            }
        } else if (queryCount != null) {
            try {
                int count = Integer.parseInt(queryCount);
                RandomQueriesGenerator gen = new RandomQueriesGenerator();
                gen.createRandomQueries(count);
                List<Company> companies = companyDAO.getAllCompanies();
                session.setAttribute("companies", companies);
                session.removeAttribute("error");
            } catch (NumberFormatException ex) {
                session.setAttribute("error", "Invalid number of queries provided.");
            } catch (DAOException ex) {
                Logger.getLogger(CompanyServlet.class.getName()).log(Level.SEVERE, null, ex);
                session.setAttribute("error", "Failed to save generated queries.");
            }
        } else {
            session.setAttribute("error", "No data provided.");
        }
       
        RequestDispatcher disp = getServletContext().getRequestDispatcher("/company.jsp");
        disp.forward(req, resp);
    }
}
