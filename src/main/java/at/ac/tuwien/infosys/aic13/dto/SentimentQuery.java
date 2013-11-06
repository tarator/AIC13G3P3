package at.ac.tuwien.infosys.aic13.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import at.ac.tuwien.infosys.aic13.dao.DTO;

@Entity
@Table(name="sentimentQuery")
public class SentimentQuery implements DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6592630286442076582L;


	@Id
    @Column(name="id")
    @GeneratedValue
	private Long id;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="company", nullable=false)
	private Company company;
	
	@Column(name="dateFrom")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date from;
	
	@Column(name="dateTo")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date to;
	
	@Column(name="processed")
	private boolean processed;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "query", cascade = CascadeType.ALL)
	private SentimentQueryResult result;
	
	
	
	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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
