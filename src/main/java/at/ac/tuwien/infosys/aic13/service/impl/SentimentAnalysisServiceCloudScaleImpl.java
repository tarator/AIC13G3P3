package at.ac.tuwien.infosys.aic13.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.infosys.aic13.service.QueryService;
import at.ac.tuwien.infosys.aic13.service.SentimentAnalysisService;

public class SentimentAnalysisServiceCloudScaleImpl implements
		SentimentAnalysisService {

	@Autowired private QueryService queryService;
	
	private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisServiceCloudScaleImpl.class);
	
	@Override
	public void runSentimentAnalysis(){
		//TODO: implement here. Start cloud worker. see dummy implementation for a Thread-based implementation.
	}

	@Override
	public void run() {
		while(true){
			try{
				runSentimentAnalysis();
			}catch(Exception e){
				logger.error("Error while processing sentiment analysis. (Z4X658)", e);
			}
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				logger.warn("Thread sleep interrupted (may be ignored).", e);
			}
		}
		
	}


}
