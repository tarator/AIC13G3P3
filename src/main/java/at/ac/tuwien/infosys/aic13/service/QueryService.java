package at.ac.tuwien.infosys.aic13.service;

import java.util.List;

import at.ac.tuwien.infosys.aic13.dto.Company;
import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;

public interface QueryService {

	/**
	 * This method returns a list of all queries of the given company.
	 * @param company
	 * @return
	 * @throws ServiceException
	 */
	public List<SentimentQuery> getQueries(Company company) throws ServiceException;
	
	
	/**
	 * This method marks a query as processed and returns it for processing.<br />
	 * The processing must be started manually.
	 * @return
	 * @throws ServiceException
	 */
	public SentimentQuery getNextQuery() throws ServiceException;
		
}
