package at.ac.tuwien.infosys.aic13.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SentimentQuery implements Serializable {

	/**
	 * 
	 */

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
    @Persistent
	private Company company;
	
    @Persistent
	private Date from;
	
    @Persistent
	private Date to;
	
    @Persistent
	private boolean processed;
	
    @Persistent
	private SentimentQueryResult result;

	public long getIdLong() {
		return this.id.getId();
	}
    
    public Key getId() {
        return this.id;
    }

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
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

	/**
	 * This flag indicates, that this query is already processed.
	 * A result may not be here yet.
	 * @return
	 */
	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public SentimentQueryResult getResult() {
		return result;
	}

	public void setResult(SentimentQueryResult result) {
		this.result = result;
	}
}
