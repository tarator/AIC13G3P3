package at.ac.tuwien.infosys.aic13.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import at.ac.tuwien.infosys.aic13.dao.DTO;

@Entity
@Table(name="sentimentQueryResult")
public class SentimentQueryResult implements DTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1831336420562966811L;
	
	@Id
    @Column(name="id")
    @GeneratedValue
	private Long id;
	
	@Column(name="numberOfTweets")
	private Integer numberOfTweets;
	
	@Column(name="sentimentValue")
	private Double sentimentValue;
	
	@OneToOne(fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private SentimentQuery query;
	
	public Integer getNumberOfTweets() {
		return numberOfTweets;
	}

	public void setNumberOfTweets(Integer numberOfTweets) {
		this.numberOfTweets = numberOfTweets;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SentimentQuery getQuery() {
		return query;
	}

	public void setQuery(SentimentQuery query) {
		this.query = query;
	}

	public Double getSentimentValue() {
		return sentimentValue;
	}

	public void setSentimentValue(Double sentimentValue) {
		this.sentimentValue = sentimentValue;
	}

}
