
package at.ac.tuwien.infosys.aic13.dao.impl;

import at.ac.tuwien.infosys.aic13.model.Company;
import at.ac.tuwien.infosys.aic13.persistence.PMF;
import at.ac.tuwien.infosys.aic13.dao.CompanyDAO;
import at.ac.tuwien.infosys.aic13.dao.DAOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.jdo.FetchGroup;
import javax.jdo.FetchPlan;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

/**
 *
 * @author Roman Khassraf <roman at khassraf.at>
 */
public class JDOCompanyDAO implements CompanyDAO {

    @Override
    public Company createCompany(Company company) throws DAOException {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        Company c;
        try {
            tx.begin();
            c = (Company) pm.makePersistent(company);
            c = pm.detachCopy(c);
            tx.commit();
        } catch (JDOUserException e) {
            throw new DAOException("Failed to persist company " + company.getName());
        } finally {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        return c;
    }
    
    @Override
    public Company updateCompany(Company company) throws DAOException {
        return createCompany(company);
    }
        
    @Override
    public List<Company> updateCompanies(Collection<Company> companies) throws DAOException {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        ArrayList<Company> newCompanies = new ArrayList<>();
        try {
            tx.begin();
            List<Company> result = (List<Company>)pm.makePersistentAll(companies);

            newCompanies = (ArrayList<Company>) pm.detachCopyAll(result);

            tx.commit();
        } catch (JDOUserException e) {
            System.out.println(e.getMessage());
            
            throw new DAOException("Failed to persist companies ");
        } finally {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        return newCompanies;
    }

    @Override
    public Company getCompany(String companyName) throws DAOException {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        pm.getFetchPlan().setGroup(FetchGroup.ALL);
        pm.getFetchPlan().setMaxFetchDepth(2);
        pm.getFetchPlan().setFetchSize(FetchPlan.FETCH_SIZE_GREEDY);
        
        Query q = pm.newQuery(Company.class);
        q.setFilter("name == companyName");
        q.declareParameters("String companyName");
        Company c;
        try {
            tx.begin();
            List<Company> queryResult = (List<Company>)q.execute(companyName);
            if (queryResult == null || queryResult.isEmpty()) {
                throw new DAOException("Could not find company with name " + companyName);
            }
            c = ((List<Company>)pm.detachCopyAll(queryResult)).get(0);
            tx.commit();
        } finally {
            if (tx.isActive())
            {
                tx.rollback();
            }
            q.closeAll();
            pm.close();
        }
        return c;
    }

    @Override
    public List<Company> getAllCompanies() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        pm.getFetchPlan().setGroup(FetchGroup.ALL);
        pm.getFetchPlan().setMaxFetchDepth(2);
        Query q = pm.newQuery(Company.class);
        ArrayList<Company> companies = new ArrayList<>();
        try {
            List queryResult = (List<Company>)q.execute();
            if (queryResult == null || queryResult.isEmpty()) {
                return companies;
            }
            companies = (ArrayList<Company>) pm.detachCopyAll(queryResult);
        } finally {
            q.closeAll();
            pm.close();
        }
        return companies;
    }

    @Override
    public boolean companyExists(String companyName) throws DAOException {
        try {
            Company c = this.getCompany(companyName);
            if (c != null) {
                return true;
            } else {
            }
            return false;
        } catch (DAOException e) {
            return false;
        }
    }
}
