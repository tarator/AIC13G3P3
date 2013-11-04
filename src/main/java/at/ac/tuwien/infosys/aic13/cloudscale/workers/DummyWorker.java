package at.ac.tuwien.infosys.aic13.cloudscale.workers;

import at.ac.tuwien.infosys.aic13.Utils;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;
import at.ac.tuwien.infosys.cloudscale.annotations.CloudObject;
import at.ac.tuwien.infosys.cloudscale.annotations.DestructCloudObject;

@CloudObject
public class DummyWorker {

	@DestructCloudObject
	public PublicSentimentQueryResult doTheAnalysisStuff(PublicSentimentQuery query){
		System.out.println("Doing query for "+query+ "("+query.getCompany().getName()+") (2WDR8S).");
		// Do sentiment analysis...
		try {
			Thread.sleep(Utils.getRandom(250, 5000));
		} catch (InterruptedException e) {
			
		}
		
		PublicSentimentQueryResult result = new PublicSentimentQueryResult();
		result.setNumberOfTweets(Utils.getRandom(10, 1000));
		result.setSentimentValue(Math.random());
		return result;
	}
}
