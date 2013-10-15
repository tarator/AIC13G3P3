package ac.at.tuwien.aic13.dto;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import ac.at.tuwien.aic13.persistence.DTO;

@Entity(name="melder")
public class Melder implements DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -170477252249623193L;
	private String appName;
	private String value;
	private List<LogEntry> logEntries;
	
	@Id
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="melder")
	public List<LogEntry> getLogEntries() {
		return logEntries;
	}
	public void setLogEntries(List<LogEntry> logEntries) {
		this.logEntries = logEntries;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appName == null) ? 0 : appName.hashCode());
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
		Melder other = (Melder) obj;
		if (appName == null) {
			if (other.appName != null)
				return false;
		} else if (!appName.equals(other.appName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return getAppName();
	}
	
	
	
	
	
}
