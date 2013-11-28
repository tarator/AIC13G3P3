package at.ac.tuwien.infosys.aic13.cloudscale.workers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.infosys.aic13.Utils;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;
import at.ac.tuwien.infosys.cloudscale.annotations.CloudObject;
import at.ac.tuwien.infosys.cloudscale.annotations.DestructCloudObject;

@CloudObject
public class DummyWorker {

	private static final Logger log = LoggerFactory.getLogger(DummyWorker.class);

	@DestructCloudObject
	public PublicSentimentQueryResult doTheAnalysisStuff(PublicSentimentQuery query) {

		// long randomSleepTime = ThreadLocalRandom.current().nextLong(100000) *
		// 2;
		long randomSleepTime = ThreadLocalRandom.current().nextLong(5000);

		long startTime = System.currentTimeMillis();
		log.info(String.format("DummyWorker will now prodcue CPU Load for %d seconds",
				TimeUnit.MILLISECONDS.toSeconds(randomSleepTime)));
		while ((System.currentTimeMillis() - startTime) < randomSleepTime) {
			// produce CPU load while waiting
		}

		PublicSentimentQueryResult result = new PublicSentimentQueryResult();
		result.setNumberOfTweets(Utils.getRandom(10, 1000));
		result.setSentimentValue(Math.random());
		return result;
	}
}
