
package at.ac.tuwien.infosys.aic13.util;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public class RandomQueriesGenerator {
    
    private static final String[] companies = {"AUDI", "BMW", "Rolex", "Microsoft", "Apple", "Wien"};
    private final CompanyDAO companyDAO = new JDOCompanyDAO();
    
    public void createRandomQueries(int count) throws DAOException {
        createSomeCompanies();
        createQueries(count);
    }
    
    private void createSomeCompanies(){
        for (String companie : companies) {
            try {
                if (!companyDAO.companyExists(companie)) {
                    Company c = new Company();
                    c.setName(companie);
                    c.setCreationDate(new Date());
                    companyDAO.createCompany(c);
                }
            }catch(DAOException e) {
                Logger.getLogger(RandomQueriesGenerator.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    private void createQueries(int count) throws DAOException {
        
        List<Company> comp = companyDAO.getAllCompanies();
        Date today = new Date();
        Date yesterday;
        Calendar cal = Calendar.getInstance();
        Random r = new Random();
                
        for (int i = 0; i < count; i++) {
            cal.setTime(today);
            cal.add(Calendar.DATE, -1);
            yesterday = cal.getTime();
            Company c = comp.get(
                    r.nextInt(comp.size())
            );
            SentimentQuery q = new SentimentQuery();
            q.setCompany(c);
            q.setFrom(yesterday);
            q.setTo(today);
            
            Set<SentimentQuery> queries = c.getQueries();
            queries.add(q);
            c.setQueries(queries);
            
            
            today = yesterday;
        }
        
        Queue queue = QueueFactory.getQueue("QueryQueue");
        for (Company c : comp) {
            c = companyDAO.updateCompany(c);
            
            for (SentimentQuery q : c.getQueries()) {
                if (!q.isProcessed()) {
                    TaskOptions taskOptions = TaskOptions.Builder.withUrl("/processquery")
                            .header("Host", BackendServiceFactory.getBackendService().getBackendAddress("processquery-backend"))
                            .param("queryid", KeyFactory.keyToString(q.getId()))
                            .param("companyname", c.getName())
                            .method(TaskOptions.Method.POST);
                    queue.add(taskOptions);
                }
            }
        }
    }
}
