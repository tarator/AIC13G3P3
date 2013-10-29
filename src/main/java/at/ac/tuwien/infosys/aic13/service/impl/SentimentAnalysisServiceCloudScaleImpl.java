package at.ac.tuwien.infosys.aic13.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.infosys.aic13.dao.GenericDao;
import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;
import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.SentimentAnalysisService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;

public class SentimentAnalysisServiceCloudScaleImpl implements
		SentimentAnalysisService {

	@Autowired private QueryService queryService;
	
	private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisServiceCloudScaleImpl.class);
	
	@Override
	public void runSentimentAnalysis() throws ServiceException {
		SentimentQuery query = queryService.getNextQuery();
		//TODO: implement here. Start cloud worker.
	}

	@Override
	public void run() {
		while(true){
			try{
				runSentimentAnalysis();
			}catch(ServiceException e){
				logger.error("Error while performing Sentiment Analysis.", e);
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				logger.warn("Thread sleep interrupted (may be ignored).", e);
			}
		}
		
	}


}
