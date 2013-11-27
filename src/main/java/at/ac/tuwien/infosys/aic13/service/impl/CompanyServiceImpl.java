package at.ac.tuwien.infosys.aic13.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.infosys.aic13.dao.DaoException;
import at.ac.tuwien.infosys.aic13.dao.GenericDao;
import at.ac.tuwien.infosys.aic13.dto.Company;
import at.ac.tuwien.infosys.aic13.service.CompanyService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;

@Transactional(readOnly=true, rollbackFor={ServiceException.class})
public class CompanyServiceImpl implements CompanyService {

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
		Company c = new Company();
		c.setName(companyName);
		Example ex = Example.create(c);
		ex.enableLike();
		ex.excludeZeroes();
		DetachedCriteria criteria = DetachedCriteria.forClass(Company.class);
		criteria.add(ex);
		List<Company> companies = null;
		try {
			companies = dao.findByDetachedCriteria(criteria,0);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		if(companies == null || companies.size() < 1) throw new ServiceException("Could not find company with name "+companyName);
		return companies.get(0);
	}

	@Override
	public List<Company> getAllCompanies() throws ServiceException {
		try {
			return dao.readAll(Company.class);
		} catch (DaoException e) {
			throw new ServiceException("Could not load company list.");
		}
	}

	@Override
	public boolean companyExists(String companyName) throws ServiceException {
		Company c = new Company();
		c.setName(companyName);
		Example ex = Example.create(c);
		ex.enableLike();
		ex.excludeZeroes();
		DetachedCriteria criteria = DetachedCriteria.forClass(Company.class);
		criteria.add(ex);
		List<Company> companies = null;
		try {
			companies = dao.findByDetachedCriteria(criteria,0);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		if(companies.size() > 0) return true;
		return false;
	}
	
	

}
