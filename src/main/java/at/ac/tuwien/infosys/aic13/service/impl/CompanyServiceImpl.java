package at.ac.tuwien.infosys.aic13.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.infosys.aic13.dao.DaoException;
import at.ac.tuwien.infosys.aic13.dao.GenericDao;
import at.ac.tuwien.infosys.aic13.dto.Company;
import at.ac.tuwien.infosys.aic13.service.CompanyService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;

@Transactional(readOnly=true)
public class CompanyServiceImpl implements CompanyService {

	@Autowired GenericDao dao;
	
	@Override
	@Transactional(readOnly=false)
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
