package ac.at.tuwien.aic13.services;

import java.util.List;

import ac.at.tuwien.aic13.dto.LogEntry;
import ac.at.tuwien.aic13.dto.Melder;
import ac.at.tuwien.aic13.persistence.exception.ServiceException;

public interface MainService {

	public void createMelder(Melder melder) throws ServiceException;
	
	public void addLogEntry(LogEntry logEntry) throws ServiceException;
	
	public List<LogEntry> getLogs(Melder melder) throws ServiceException; 
	
	public List<Melder> getMelder() throws ServiceException; 
}
