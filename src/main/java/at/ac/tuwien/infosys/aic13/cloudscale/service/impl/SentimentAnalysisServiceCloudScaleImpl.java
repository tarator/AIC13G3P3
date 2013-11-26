package at.ac.tuwien.infosys.aic13.cloudscale.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.infosys.aic13.cloudscale.service.SentimentAnalysisService;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;
import at.ac.tuwien.infosys.aic13.sentiment.impl.TwitterSentimentAnalyzerImpl;

public class SentimentAnalysisServiceCloudScaleImpl extends SentimentAnalysisService {	
	
	private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisServiceCloudScaleImpl.class);
	
	@Override
	public void runSentimentAnalysis(){
	    SentimentAnalysisRunner runner = new SentimentAnalysisRunner();
	    Thread t = new Thread(runner);
	    t.setDaemon(true);
	    t.start();
	}

    private class SentimentAnalysisRunner implements Runnable{
        
        private TwitterSentimentAnalyzerImpl tsa;

        @Override
        public void run() {
            PublicSentimentQuery query = getNextQuery();
            if(query == null || query.getQueryId() == null) return;
            
            tsa = new TwitterSentimentAnalyzerImpl();
            logger.debug("Starting Analyzer...");
            // Do sentiment analysis...
            PublicSentimentQueryResult result = tsa.analyze(query);
            if(result != null)
            result.setQueryId(query.getQueryId());
            writeResult(result);
            
        }
			
    }

}
