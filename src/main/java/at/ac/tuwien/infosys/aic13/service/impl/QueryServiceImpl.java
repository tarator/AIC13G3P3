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
import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;

@Transactional(readOnly=true, rollbackFor={ServiceException.class})
public class QueryServiceImpl implements QueryService {

	@Autowired private GenericDao dao;
	
	@Override
	public List<SentimentQuery> getQueries(Company company)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(SentimentQuery.class);
//		Example queryEx = Example.create(new SentimentQuery());
//		queryEx.excludeZeroes();
//		queryEx.excludeProperty("isProcessed");
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
	public SentimentQuery getNextQuery() throws ServiceException {
		throw new ServiceException("Service not implemented yet.");
	}

}
