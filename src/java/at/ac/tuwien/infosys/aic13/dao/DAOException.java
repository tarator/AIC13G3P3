package at.ac.tuwien.infosys.aic13.dao;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2487077871888637562L;

	public DAOException(){
		super();
	}
	
	public DAOException(Throwable t){
		super(t);
	}
	
	public DAOException(String message){
		super(message);
	}
	
	public DAOException(String message, Throwable t){
		super(message, t);
	}
}
