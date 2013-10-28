package at.ac.tuwien.infosys.aic13.service;

public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2487077871888637562L;

	public ServiceException(){
		super();
	}
	
	public ServiceException(Throwable t){
		super(t);
	}
	
	public ServiceException(String message){
		super(message);
	}
	
	public ServiceException(String message, Throwable t){
		super(message, t);
	}
}
