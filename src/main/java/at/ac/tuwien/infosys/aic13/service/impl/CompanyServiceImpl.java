package at.ac.tuwien.infosys.aic13.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.infosys.aic13.dao.DaoException;
import at.ac.tuwien.infosys.aic13.dao.GenericDao;
import at.ac.tuwien.infosys.aic13.dto.Company;
import at.ac.tuwien.infosys.aic13.service.CompanyService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;

@Transactional(readOnly=true, rollbackFor={ServiceException.class})
public class CompanyServiceImpl implements CompanyService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class); 
	@Autowired GenericDao dao;
	
	@Override
	@Transactional(readOnly=false, rollbackFor={ServiceException.class})
	public void createCompany(Company company) throws ServiceException{
		try {
			dao.create(company);
		} catch (DaoException e) {
			throw new ServiceException("Could not create company.", e);
		}

	}

	@Override
	public Company getCompany(String companyName) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
