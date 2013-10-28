package at.ac.tuwien.infosys.aic13.dao;

public class DaoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3620038229061597129L;
	
	
	public DaoException(){
		super();
	}
	
	public DaoException(Throwable t){
		super(t);
	}
	
	public DaoException(String message){
		super(message);
	}
	
	public DaoException(String message, Throwable t){
		super(message, t);
	}

}
