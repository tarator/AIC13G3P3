package at.ac.tuwien.infosys.aic13.service;


public interface SentimentAnalysisService extends Runnable{

	/**
	 * This method starts the CloudWorker and sets the result after the worker is done.
	 * May have a long runtime! THe query should be marked as processed before.
	 * If an error occurs while running the Sentiment analysis and no SentimentQueryResult can be created, the query must be marked as unprocessed.
	 * @param query
	 * @throws ServiceException - if query or dao are not set, or anything else fails..
	 */
	public void runSentimentAnalysis() throws ServiceException;
}
