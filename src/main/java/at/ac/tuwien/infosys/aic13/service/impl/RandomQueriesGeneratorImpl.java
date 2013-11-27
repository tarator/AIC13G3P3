package at.ac.tuwien.infosys.aic13.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.infosys.aic13.Utils;
import at.ac.tuwien.infosys.aic13.dto.Company;
import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;
import at.ac.tuwien.infosys.aic13.service.CompanyService;
import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.RandomQueriesGenerator;
import at.ac.tuwien.infosys.aic13.service.ServiceException;

public class RandomQueriesGeneratorImpl implements RandomQueriesGenerator {

	
	
	private static final Logger logger = LoggerFactory.getLogger(RandomQueriesGeneratorImpl.class);
	private static final String[] companies = {"AUDI", "BMW", "Rolex", "Microsoft", "Apple", "Wien"};
	
	@Autowired private CompanyService companyService;
	@Autowired private QueryService queryService;
	
	@Override
	public void createRandomQueries(int count){
		Runner x = new Runner(count);
		new Thread(x).start();
	}
	
	private static Company pickRandom(List<Company> companies){
		return companies.get(Utils.getRandom(0, companies.size()-1));
	}
	
	private void createSomeCompanies(){
		for(int i = 0; i< companies.length; i++){
			try{			
				if(!companyService.companyExists(companies[i])){
					Company c = new Company();
					c.setName(companies[i]);
					c.setCreationDate(new Date());
					companyService.createCompany(c);
				}
			}catch(ServiceException e){
				logger.warn("Could not create company. \""+companies[i]+"\" (8Z71WT).", e);
			}
			
		}
	}
	
	private class Runner implements Runnable{
		private int count;
		public Runner(int count){
			this.count = count;
		}
		@Override
		public void run() {
			createSomeCompanies();
			
			
			long today = (new Date()).getTime();
			long oneYearAgo = today - (1000*60*60*24*365);
			try {
				List<Company> companies = companyService.getAllCompanies();
				for(int i = 0; i < count; i++){
					SentimentQuery q = new SentimentQuery();
					q.setCompany(pickRandom(companies));
					Date d = new Date(Utils.getRandom(oneYearAgo, today));
					q.setFrom(Utils.getDateMinusHours(d, Utils.getRandom(24, 24*7)));
					q.setTo(d);
					queryService.createQuery(q);
				}
			} catch (ServiceException e) {
				logger.error("Error while creating random queries.", e);
			}
		}
	}

}
