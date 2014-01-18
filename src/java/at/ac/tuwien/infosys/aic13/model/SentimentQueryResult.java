package at.ac.tuwien.infosys.aic13.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SentimentQueryResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1831336420562966811L;
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
    @Persistent
	private Integer numberOfTweets;
	
    @Persistent
	private Double sentimentValue;
	
	public Integer getNumberOfTweets() {
		return numberOfTweets;
	}

	public void setNumberOfTweets(Integer numberOfTweets) {
		this.numberOfTweets = numberOfTweets;
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Double getSentimentValue() {
		return sentimentValue;
	}

	public void setSentimentValue(Double sentimentValue) {
		this.sentimentValue = sentimentValue;
	}
}
