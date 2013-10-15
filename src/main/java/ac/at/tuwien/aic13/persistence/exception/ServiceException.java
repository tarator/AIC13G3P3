package ac.at.tuwien.aic13.persistence.exception;

/**
 * The Class ServiceException.
 * This exception is thrown on Service failure situations.
 */
public class ServiceException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3585794844515356703L;

	/**
	 * Instantiates a new service exception.
	 */
	public ServiceException(){
		super("No reason given");
	}
	
	/**
	 * Instantiates a new service exception.
	 *
	 * @param cause the cause
	 */
	public ServiceException(Throwable cause){
		super(cause);
	}
	
	/**
	 * Instantiates a new service exception.
	 *
	 * @param message the message
	 */
	public ServiceException(String message){
		super(message);
	}
	
	/**
	 * Instantiates a new service exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ServiceException(String message, Throwable cause){
		super(message, cause);
	}	
}
