package at.ac.tuwien.infosys.aic13.cloudscale.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.infosys.aic13.cloudscale.service.SentimentAnalysisService;
import at.ac.tuwien.infosys.aic13.cloudscale.workers.DummyWorker;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;

public class SentimentAnalysisServiceDummyImpl extends SentimentAnalysisService {

    private static final Logger log = LoggerFactory.getLogger(SentimentAnalysisServiceDummyImpl.class);

    @Override
    public void runSentimentAnalysis(){
	SentimentAnalysisRunner runner = new SentimentAnalysisRunner();
	Thread t = new Thread(runner);
	t.setDaemon(true);
	t.start();
    }

    private class SentimentAnalysisRunner implements Runnable{

	@Override
	public void run() {
	    PublicSentimentQuery publicQuery = getNextQuery();
	    if(publicQuery == null) return;

	    // Creating and starting a worker... this runs through AOP eaving n cloudscale.
	    DummyWorker worker = new DummyWorker();		
	    PublicSentimentQueryResult publicResult = worker.doTheAnalysisStuff(publicQuery);
	    publicResult.setQueryId(publicQuery.getQueryId());
	    writeResult(publicResult);

	}

    }
}
