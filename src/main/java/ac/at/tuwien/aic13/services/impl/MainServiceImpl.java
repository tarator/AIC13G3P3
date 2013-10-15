package ac.at.tuwien.aic13.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ac.at.tuwien.aic13.dto.LogEntry;
import ac.at.tuwien.aic13.dto.Melder;
import ac.at.tuwien.aic13.services.MainService;
import ac.at.tuwien.aic13.persistence.GenericDao;
import ac.at.tuwien.aic13.persistence.exception.ServiceException;

@Transactional(readOnly=true, rollbackFor={ServiceException.class})
public class MainServiceImpl implements MainService {

	@Autowired private GenericDao dao;
	
	@Override
	@Transactional(readOnly=false, rollbackFor={ServiceException.class})
	public void createMelder(Melder melder) throws ServiceException {
		dao.create(melder);
		
	}
	
	@Override
	@Transactional(readOnly=false, rollbackFor={ServiceException.class})
	public void addLogEntry(LogEntry logEntry) throws ServiceException {
		dao.create(logEntry);
		
	}

	@Override
	public List<LogEntry> getLogs(Melder melder) throws ServiceException {
		return dao.readAll(LogEntry.class);
	}

	@Override
	public List<Melder> getMelder() throws ServiceException {
		return dao.readAll(Melder.class);
	}


}
