
package at.ac.tuwien.infosys.aic13.controller;

import at.ac.tuwien.infosys.aic13.dao.CompanyDAO;
import at.ac.tuwien.infosys.aic13.dao.DAOException;
import at.ac.tuwien.infosys.aic13.dao.impl.JDOCompanyDAO;
import at.ac.tuwien.infosys.aic13.model.Company;
import at.ac.tuwien.infosys.aic13.model.SentimentQuery;
import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
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
public class QueryServlet extends HttpServlet {
    
    private final CompanyDAO companyDAO = new JDOCompanyDAO();
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(true);
              
        String companyName = req.getPathInfo().replaceAll("/", "");
        try {
            Company company = companyDAO.getCompany(companyName);
            session.setAttribute("company", company);
            session.setAttribute("queries", company.getQueries());
        } catch (DAOException ex) {
            Logger.getLogger(QueryServlet.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("error", "Company not found");
        }
        
        session.setAttribute("newQueryTo", new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        session.setAttribute("newQueryFrom", cal.getTime());
        
        session.removeAttribute("error");
        
        RequestDispatcher disp = getServletContext().getRequestDispatcher("/queries.jsp");
        disp.forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(true);
        
        String queryFrom = req.getParameter("queryFrom");
        String queryTo = req.getParameter("queryTo");

        String companyName = req.getPathInfo().replaceAll("/", "");
        try {
            Company company = companyDAO.getCompany(companyName);
            
            SimpleDateFormat sdf = new SimpleDateFormat( "dd.MM.yyyy" );
            
            SentimentQuery sq = new SentimentQuery();
            sq.setCompany(company);
            sq.setFrom(sdf.parse(queryFrom));
            sq.setTo(sdf.parse(queryTo));
            
            Set<SentimentQuery> queries = company.getQueries();
            
            queries.add(sq);
            company.setQueries(queries);
            companyDAO.updateCompany(company);          
            
            Queue queue = QueueFactory.getQueue("QueryQueue");
            TaskOptions taskOptions = TaskOptions.Builder.withUrl("/processquery")
                    .header("Host", BackendServiceFactory.getBackendService().getBackendAddress("processquery-backend"))
                    .param("queryid", KeyFactory.keyToString(sq.getId()))
                    .param("companyname", company.getName())
                    .method(Method.POST);
            queue.add(taskOptions);

            company = companyDAO.getCompany(companyName);
            
            session.setAttribute("company", company);
            session.setAttribute("queries", company.getQueries());
            session.removeAttribute("error");
        } catch (DAOException ex) {
            Logger.getLogger(QueryServlet.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("error", "Database Error.");
        } catch (ParseException ex) {
            Logger.getLogger(QueryServlet.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("error", "Invalid date was provided");
        }
        
        RequestDispatcher disp = getServletContext().getRequestDispatcher("/queries.jsp");
        disp.forward(req, resp);
    }
}
