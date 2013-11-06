package at.ac.tuwien.infosys.aic13.cloudscale.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.infosys.aic13.cloudscale.service.SentimentAnalysisService;
import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;
import at.ac.tuwien.infosys.aic13.dto.SentimentQueryResult;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;
import at.ac.tuwien.infosys.aic13.sentiment.TwitterSentimentAnalyzer;
import at.ac.tuwien.infosys.aic13.sentiment.impl.TwitterSentimentAnalyzerImpl;
import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.ServiceException;

public class SentimentAnalysisServiceCloudScaleImpl extends
		SentimentAnalysisService {	
	private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisServiceCloudScaleImpl.class);
	
	@Override
	public void runSentimentAnalysis(){
        PublicSentimentQuery query = getNextQuery();
        if(query == null) return;
        SentimentAnalysisRunner runner = 
                new SentimentAnalysisRunner(query);
        Thread t = new Thread(runner);
        t.start();
	}

    private class SentimentAnalysisRunner implements Runnable{
        
        private TwitterSentimentAnalyzer tsa = new TwitterSentimentAnalyzerImpl();
        private PublicSentimentQuery query = null;
        
        public SentimentAnalysisRunner(PublicSentimentQuery query) {
            this.query = query;
        }
        @Override
        public void run() {
            
            if(query == null) return;
            logger.debug("Starting Analyzer");
            
            // Do sentiment analysis...
            PublicSentimentQueryResult result = tsa.analyze(query);
            
//            try {
//                queryService.saveResult(result);
//            } catch (ServiceException e) {
//                logger.error("Could not save result for query (G5QC4C).", e);
//            }

        }
			
    }

	@Override
	public void stopService() {
		// TODO Auto-generated method stub
		
	}
}
