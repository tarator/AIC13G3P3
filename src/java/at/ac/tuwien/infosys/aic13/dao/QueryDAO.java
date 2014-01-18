package at.ac.tuwien.infosys.aic13.dao;

import at.ac.tuwien.infosys.aic13.model.SentimentQuery;


public interface QueryDAO {
	
	public SentimentQuery createQuery(SentimentQuery query) throws DAOException;
    
    public SentimentQuery updateQuery(SentimentQuery query) throws DAOException;
    
    public SentimentQuery getQueryById(String queryId) throws DAOException;
		
}
