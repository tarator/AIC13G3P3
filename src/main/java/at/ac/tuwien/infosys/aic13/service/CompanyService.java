package at.ac.tuwien.infosys.aic13.service;

import java.util.List;

import at.ac.tuwien.infosys.aic13.dto.Company;

public interface CompanyService {

	public void createCompany(Company company) throws ServiceException;
	
	public Company getCompany(String companyName) throws ServiceException;
	
	/**
	 * Returns a list of all companies. Could return an empty list, but never null.
	 * @return
	 * @throws ServiceException
	 */
	public List<Company> getAllCompanies() throws ServiceException;
	
}
