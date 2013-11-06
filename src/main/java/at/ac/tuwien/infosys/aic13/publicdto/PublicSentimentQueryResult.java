package at.ac.tuwien.infosys.aic13.publicdto;

import java.io.Serializable;

public class PublicSentimentQueryResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5340533061979605521L;
	private Long queryId;
	private Integer numberOfTweets;
	private Double sentimentValue;
	
	public PublicSentimentQueryResult(){
		
	}
	
	public PublicSentimentQueryResult(Integer numberOfTweets, Double sentimentValue){
		this.setNumberOfTweets(numberOfTweets);
		this.setSentimentValue(sentimentValue);
	}
	
	public Integer getNumberOfTweets() {
		return numberOfTweets;
	}
	public void setNumberOfTweets(Integer numberOfTweets) {
		this.numberOfTweets = numberOfTweets;
	}
	public Double getSentimentValue() {
		return sentimentValue;
	}
	public void setSentimentValue(Double sentimentValue) {
		this.sentimentValue = sentimentValue;
	}

	public Long getQueryId() {
		return queryId;
	}

	public void setQueryId(Long queryId) {
		this.queryId = queryId;
	}
}
