package at.ac.tuwien.infosys.aic13.service;

import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;

public interface SentimentAnalysisService {

	/**
	 * This method starts the CloudWorker and sets the result after the worker is done.
	 * May have a long runtime! THe query should be marked as processed before.
	 * If an error occurs while running the Sentiment analysis and no SentimentQueryResult can be created, the query must be marked as unprocessed.
	 * @param query
	 * @throws ServiceException
	 */
	public void runSentimentAnalysis(SentimentQuery query) throws ServiceException;
}
