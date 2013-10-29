package at.ac.tuwien.infosys.aic13.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.ac.tuwien.infosys.aic13.dao.GenericDao;
import at.ac.tuwien.infosys.aic13.dao.HibernateDaoImpl;
import at.ac.tuwien.infosys.aic13.service.CompanyService;
import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.SentimentAnalysisService;
import at.ac.tuwien.infosys.aic13.service.impl.CompanyServiceImpl;
import at.ac.tuwien.infosys.aic13.service.impl.QueryServiceImpl;
import at.ac.tuwien.infosys.aic13.service.impl.SentimentAnalysisServiceDummyImpl;

@Component
public class WebAppConfig {

	private static final Logger logger = LoggerFactory.getLogger(WebAppConfig.class);
	
	@Autowired(required=true)
	private Properties g3p3Properties;
	
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
		String className = (String) g3p3Properties.get("g3p3.sentimentAnalysisServiceClass");
		SentimentAnalysisService service = null;
		if(className != null){
			try {
				@SuppressWarnings("unchecked")
				Class<SentimentAnalysisService> c = (Class<SentimentAnalysisService>) Class.forName(className);
				service = c.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				logger.error("Failed to create SentimentAnalysisServiceClass from property '"+className+"' Defaulting to Dummy-Service (PKSZNR).", e);
			}
		}
		if(service == null){
			logger.warn("Defaulting to Dummy-SentimanetAnalysisService (A1FF8Z).");
			service = new SentimentAnalysisServiceDummyImpl();
		}
		Thread t = new Thread(service);
		t.start();
		return service;
	}
	
}
