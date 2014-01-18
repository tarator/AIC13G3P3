
package at.ac.tuwien.infosys.aic13.controller;

import at.ac.tuwien.infosys.aic13.dao.DAOException;
import at.ac.tuwien.infosys.aic13.dao.QueryDAO;
import at.ac.tuwien.infosys.aic13.dao.impl.JDOQueryDAO;
import at.ac.tuwien.infosys.aic13.model.SentimentQuery;
import at.ac.tuwien.infosys.aic13.sentiment.TwitterSentimentAnalyzer;
import at.ac.tuwien.infosys.aic13.sentiment.impl.TwitterSentimentAnalyzerImpl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public class ProcessQueryServlet extends HttpServlet {
    
    private final QueryDAO queryDAO = new JDOQueryDAO();
    private final TwitterSentimentAnalyzer analyzer = new TwitterSentimentAnalyzerImpl();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
          processQuery(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(ProcessQueryServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

  @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processQuery(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(ProcessQueryServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
  
    private void processQuery(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/text; charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache");
        
        String queryId = req.getParameter("queryid");
        
        try {
            SentimentQuery sq = queryDAO.getQueryById(queryId);
            sq = analyzer.analyze(sq);
            queryDAO.updateQuery(sq);
        } catch (DAOException ex) {
            Logger.getLogger(ProcessQueryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(ProcessQueryServlet.class.getName()).log(Level.INFO, "Processed Query");
    }
}
