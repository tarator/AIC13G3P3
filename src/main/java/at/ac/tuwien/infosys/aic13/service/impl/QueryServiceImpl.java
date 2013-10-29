package at.ac.tuwien.infosys.aic13.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.infosys.aic13.dao.DaoException;
import at.ac.tuwien.infosys.aic13.dao.GenericDao;
import at.ac.tuwien.infosys.aic13.dto.Company;
import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;
import at.ac.tuwien.infosys.aic13.dto.SentimentQueryResult;
import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;

@Transactional(readOnly=true, rollbackFor={ServiceException.class})
public class QueryServiceImpl implements QueryService {

	@Autowired private GenericDao dao;
	
	@Override
	public List<SentimentQuery> getQueries(Company company)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(SentimentQuery.class);
		Example compEx = Example.create(company);
		compEx.enableLike();
		compEx.excludeZeroes();
		criteria = criteria.createCriteria("company").add(compEx);
		
		try {
			return dao.findByDetachedCriteria(criteria);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	@Transactional(readOnly=false, rollbackFor={ServiceException.class})
	public SentimentQuery getNextQuery() throws ServiceException {
		SentimentQuery query = new SentimentQuery();
		query.setProcessed(false);
		Example ex = Example.create(query);
		ex.excludeZeroes();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(SentimentQuery.class);
		criteria.add(ex);
		
		List<SentimentQuery> result = null;
		try {
			result = dao.findByDetachedCriteria(criteria, 0, 1);
		} catch (DaoException e) {
			throw new ServiceException("Error while getting sentiment queries (IFQV0B).", e);
		}
		if(result == null || result.isEmpty()) return null;
		
		
		query = result.get(0);
		query.setProcessed(true);
		try {
			dao.update(query);
		} catch (DaoException e) {
			throw new ServiceException("Error while marking sentiment query to status processed. (PAQYK9).", e);
		}
		
		return query;
		
		
	}

	@Override
	public void markUnprocessed(SentimentQuery query) throws ServiceException {
		throw new ServiceException("Mark unprocessed not implemented yet.");
		
	}

	@Override
	public SentimentQuery saveResult(SentimentQueryResult result)
			throws ServiceException {
		throw new ServiceException("Save query result not implemented yet.");
	}

	@Override
	@Transactional(readOnly=false, rollbackFor={ServiceException.class})
	public void createQuery(SentimentQuery query) throws ServiceException {
		try {
			dao.create(query);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

}
