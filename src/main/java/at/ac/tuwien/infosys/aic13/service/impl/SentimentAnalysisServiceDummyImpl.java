package at.ac.tuwien.infosys.aic13.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;
import at.ac.tuwien.infosys.aic13.dto.SentimentQueryResult;
import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.SentimentAnalysisService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;

public class SentimentAnalysisServiceDummyImpl implements SentimentAnalysisService {

		@Autowired private QueryService queryService;
		
		private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisServiceCloudScaleImpl.class);
		
		@Override
		public void runSentimentAnalysis() throws ServiceException {
			SentimentAnalysisRunner runner = new SentimentAnalysisRunner();
			Thread t = new Thread(runner);
			t.start();
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
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					logger.warn("Thread.sleep interrupted. This may be ignored (0MPTFS).", e);
				}
			}
			
		}
		
		
		private class SentimentAnalysisRunner implements Runnable{

			@Override
			public void run() {
				SentimentQuery query = null;
				try {
					query = queryService.getNextQuery();
				} catch (ServiceException e1) {
					logger.error("Error while obtaining next query to process (QT4ZTV).", e1 );
				}
				if(query == null) return;
				
				
				// Do sentiment analysis...
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					
				}
				SentimentQueryResult result = new SentimentQueryResult();
				result.setNumberOfTweets(300);
				result.setSentimentValue(0.5);
				try {
					queryService.saveResult(result);
				} catch (ServiceException e) {
					logger.error("Could not save result for query (G5QC4C).", e);
				}
				
			}
			
		}
}
