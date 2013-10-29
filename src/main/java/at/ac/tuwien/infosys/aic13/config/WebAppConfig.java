package at.ac.tuwien.infosys.aic13.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import at.ac.tuwien.infosys.aic13.dao.GenericDao;
import at.ac.tuwien.infosys.aic13.dao.HibernateDaoImpl;
import at.ac.tuwien.infosys.aic13.service.CompanyService;
import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.SentimentAnalysisService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;
import at.ac.tuwien.infosys.aic13.service.impl.CompanyServiceImpl;
import at.ac.tuwien.infosys.aic13.service.impl.QueryServiceImpl;
import at.ac.tuwien.infosys.aic13.service.impl.SentimentAnalysisServiceDummyImpl;



@Configuration
public class WebAppConfig {

	@Bean()
	@Scope(value="singleton")
	public GenericDao genericDao() {
		return new HibernateDaoImpl();
	}
	
	@Bean(name="companyService")
	public CompanyService companyService(){
		return new CompanyServiceImpl();
	}
	
	@Bean(name="queryService")
	public QueryService queryService(){
		return new QueryServiceImpl();
	}
	
	@Bean(name="sentimentAnalysisService")
	public SentimentAnalysisService sentimentAnalysisService(){
		SentimentAnalysisService s = new SentimentAnalysisServiceDummyImpl();
		Thread t = new Thread(s);
		t.start();
		return s;
	}
	
}
