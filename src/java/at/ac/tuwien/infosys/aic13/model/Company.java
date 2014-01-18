package at.ac.tuwien.infosys.aic13.model;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Company implements Serializable {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
	
    @Persistent
    @Unique(name="COMPANYNAME_IDX")
    private String name;
		
    @Persistent
	private Date creationDate;

    @Persistent(mappedBy="company")
	private Set<SentimentQuery> queries;

	public Key getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public Company(){
		
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Set<SentimentQuery> getQueries() {
        if (queries == null) {
            return new HashSet<>();
        }
		return queries;
	}

	public void setQueries(Set<SentimentQuery> queries) {
		this.queries = queries;
	}
}
