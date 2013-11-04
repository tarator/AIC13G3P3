package at.ac.tuwien.infosys.aic13.publicdto;

import java.io.Serializable;
import java.util.Date;

import at.ac.tuwien.infosys.aic13.dto.SentimentQuery;

public class PublicSentimentQuery implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7420009167323797625L;
	
	private PublicCompany company;
	private Date from;
	private Date to;
	
	public PublicSentimentQuery(){
		
	}
	
	public PublicSentimentQuery(SentimentQuery query){
		this.setCompany(new PublicCompany(query.getCompany()));
		this.setFrom(query.getFrom());
		this.setTo(query.getTo());
	}

	public PublicCompany getCompany() {
		return company;
	}

	public void setCompany(PublicCompany company) {
		this.company = company;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}
	
	

}
