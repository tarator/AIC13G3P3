package at.ac.tuwien.infosys.aic13.dao;

import at.ac.tuwien.infosys.aic13.model.Company;
import java.util.Collection;
import java.util.List;


public interface CompanyDAO {

	public Company createCompany(Company company) throws DAOException;
    
    public Company updateCompany(Company company) throws DAOException;
    
    public List<Company> updateCompanies(Collection<Company> companies) throws DAOException;
	
	public Company getCompany(String companyName) throws DAOException;

	public List<Company> getAllCompanies();
	
	public boolean companyExists(String companyName) throws DAOException;
	
}
