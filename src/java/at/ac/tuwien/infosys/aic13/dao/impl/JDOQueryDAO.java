
package at.ac.tuwien.infosys.aic13.dao.impl;

import at.ac.tuwien.infosys.aic13.dao.DAOException;
import at.ac.tuwien.infosys.aic13.dao.QueryDAO;
import at.ac.tuwien.infosys.aic13.model.SentimentQuery;
import at.ac.tuwien.infosys.aic13.persistence.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import javax.jdo.FetchGroup;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public class JDOQueryDAO implements QueryDAO {
    
    @Override
    public SentimentQuery createQuery(SentimentQuery query) throws DAOException {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        SentimentQuery q = null;
        try {
            tx.begin();
            q = (SentimentQuery) pm.makePersistent(query);
            q = pm.detachCopy(q);
            tx.commit();
        } catch (JDOUserException e) {
            throw new DAOException("Failed to persist query ");
        } finally {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        return q;
    }

    @Override
    public SentimentQuery updateQuery(SentimentQuery query) throws DAOException {
        return createQuery(query);
    }
    
    @Override
    public SentimentQuery getQueryById(String queryId) throws DAOException {

        PersistenceManager pm = PMF.get().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        
        pm.getFetchPlan().setGroup(FetchGroup.ALL);
        
        Key key = KeyFactory.stringToKey(queryId); 
        
        SentimentQuery sq = null;
        try {
            tx.begin();
            
            SentimentQuery result = (SentimentQuery) pm.getObjectById(SentimentQuery.class, key);
            
            if (result != null) {
                sq = pm.detachCopy(result);
            }
            tx.commit();
        } finally {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        return sq;
    }
}
