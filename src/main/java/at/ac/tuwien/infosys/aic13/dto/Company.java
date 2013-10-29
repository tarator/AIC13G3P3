package at.ac.tuwien.infosys.aic13.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import at.ac.tuwien.infosys.aic13.dao.DTO;


@Entity
@Table(name="company")
public class Company implements DTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7917510612718054608L;
	
	@Id
    @Column(name="id")
    @GeneratedValue
    private Long id;
	
	@Column(name="name", unique=true, nullable=false)
    private String name;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="creationDate", nullable=false)
	private Date creationDate;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="company")
	private Set<SentimentQuery> queries;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Set<SentimentQuery> getQueries() {
		return queries;
	}

	public void setQueries(Set<SentimentQuery> queries) {
		this.queries = queries;
	}
	
	
	
	
	

}
