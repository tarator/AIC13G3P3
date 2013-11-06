package at.ac.tuwien.infosys.aic13.cloudscale.service.impl;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.infosys.aic13.Utils;
import at.ac.tuwien.infosys.aic13.cloudscale.service.SentimentAnalysisService;
import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;
import at.ac.tuwien.infosys.aic13.dto.SentimentQueryResult;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;
import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;

public class SentimentAnalysisServiceDummyImpl implements SentimentAnalysisService {

		private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisServiceCloudScaleImpl.class);
		
		@Override
		public void runSentimentAnalysis(){
			SentimentAnalysisRunner runner = new SentimentAnalysisRunner();
			Thread t = new Thread(runner);
			t.start();
		}

		@Override
		public void run() {
			//Wait until the spring container is configured.
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				logger.warn("Thread.sleep interrupted. This may be ignored (7HQZPV).", e1);
			}
//			long delay = Long.valueOf(g3p3Properties.getProperty("g3p3.sentimentnalysis.delayms", "250"));
			long delay = 2000;
			while(true){
				try{
					runSentimentAnalysis();
				}catch(Exception e){
					logger.error("Error while performing dummy analysis. (RPZWSU)", e);
				}
				
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					logger.warn("Thread.sleep interrupted. This may be ignored (0MPTFS).", e);
				}
			}
			
		}
		
		
		private class SentimentAnalysisRunner implements Runnable{

			@Override
			public void run() {
				SentimentQuery query = null;
//				try {
//					query = queryService.getNextQuery();
//				} catch (ServiceException e1) {
//					logger.error("Error while obtaining next query to process (QT4ZTV).", e1 );
//				}
				if(query == null) return;
				
				// Creating and starting a worker... this runs through AOP eaving n cloudscale.
//				DummyWorker worker = new DummyWorker();		
//				
//				PublicSentimentQuery publicQuery = new PublicSentimentQuery(query);
//				PublicSentimentQueryResult publicResult = worker.doTheAnalysisStuff(publicQuery);
//				
//				SentimentQueryResult result = new SentimentQueryResult();
//				result.setQuery(query);
//				result.setNumberOfTweets(publicResult.getNumberOfTweets());
//				result.setSentimentValue(publicResult.getSentimentValue());
//				
//				try {
//					queryService.saveResult(result);
//				} catch (ServiceException e) {
//					logger.error("Could not save result for query (G5QC4C).", e);
//				}
				
			}
			
		}
}
