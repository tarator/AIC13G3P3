package at.ac.tuwien.infosys.aic13.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.infosys.aic13.dao.GenericDao;
import at.ac.tuwien.infosys.aic13.dto.Company;
import at.ac.tuwien.infosys.aic13.service.CompanyService;

@Transactional(readOnly=true)
public class CompanyServiceImpl implements CompanyService {

	@Autowired GenericDao dao;
	
	@Override
	@Transactional(readOnly=false)
	public void createCompany(Company company) {
		dao.create(company);

	}

}
