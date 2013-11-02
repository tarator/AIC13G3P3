package at.ac.tuwien.infosys.aic13.service.impl;

import at.ac.tuwien.infosys.aic13.Utils;
import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;
import at.ac.tuwien.infosys.aic13.dto.SentimentQueryResult;
import at.ac.tuwien.infosys.aic13.sentiment.TwitterSentimentAnalyzer;
import at.ac.tuwien.infosys.aic13.sentiment.impl.TwitterSentimentAnalyzerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.SentimentAnalysisService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;
import java.util.Properties;

public class SentimentAnalysisServiceCloudScaleImpl implements
		SentimentAnalysisService {

	@Autowired private QueryService queryService;
	
	private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisServiceCloudScaleImpl.class);
	
	@Override
	public void runSentimentAnalysis(){
        SentimentQuery query = null;
        try {
            query = queryService.getNextQuery();
        } catch (ServiceException e1) {
            logger.error("Error while obtaining next query to process (QT4ZTV).", e1 );
        }
        if(query == null) return;
        SentimentAnalysisServiceCloudScaleImpl.SentimentAnalysisRunner runner = 
                new SentimentAnalysisServiceCloudScaleImpl.SentimentAnalysisRunner(query);
        Thread t = new Thread(runner);
        t.start();
	}

	@Override
	public void run() {
        logger.debug("Starting Analysis thread.");
		while(true){
			try{
				runSentimentAnalysis();
			} catch(Exception e){
				logger.error("Error while processing sentiment analysis. (Z4X658)", e);
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.warn("Thread sleep interrupted (may be ignored).", e);
			}
		}
		
	}

    private class SentimentAnalysisRunner implements Runnable{
        
        private TwitterSentimentAnalyzer tsa = new TwitterSentimentAnalyzerImpl();
        private SentimentQuery query = null;
        
        public SentimentAnalysisRunner(SentimentQuery query) {
            this.query = query;
        }
        @Override
        public void run() {
            
            if(query == null) return;
            logger.debug("Starting Analyzer");
            
            // Do sentiment analysis...
            SentimentQueryResult result = tsa.analyze(query);
            
            try {
                queryService.saveResult(result);
            } catch (ServiceException e) {
                logger.error("Could not save result for query (G5QC4C).", e);
            }

        }
			
    }
}
