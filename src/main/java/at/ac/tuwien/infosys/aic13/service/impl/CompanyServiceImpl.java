package at.ac.tuwien.infosys.aic13.service.impl;

import java.util.Date;
import java.util.List;

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
	public Company createCompany(Company company) throws ServiceException{
		if(company == null  || company.getName() == null || company.getName().trim().length()<2){
			throw new ServiceException("Company Name must contain at least 2 characters.");
		}
		company.setCreationDate(new Date());
		try {
			dao.create(company);
		} catch (DaoException e) {
			throw new ServiceException("Could not create company.", e);
		}
		return company;

	}

	@Override
	public Company getCompany(String companyName) throws ServiceException {
		throw new ServiceException("This function is not implemented yet.");
	}

	@Override
	public List<Company> getAllCompanies() throws ServiceException {
		try {
			return dao.readAll(Company.class);
		} catch (DaoException e) {
			throw new ServiceException("Could not load company list.");
		}
	}
	
	

}
