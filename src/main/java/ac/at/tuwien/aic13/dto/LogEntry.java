package ac.at.tuwien.aic13.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;

import ac.at.tuwien.aic13.persistence.DTO;

@Entity(name="LogEntry")
public class LogEntry implements DTO{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5086578782239196090L;
	private Long id;
	private Melder melder;
	private Date ts;
	private String ip;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="melder", nullable=false)
	public Melder getMelder() {
		return melder;
	}
	public void setMelder(Melder melder) {
		this.melder = melder;
	}

	
	@OrderBy(value="DESC")
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Id()
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogEntry other = (LogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
