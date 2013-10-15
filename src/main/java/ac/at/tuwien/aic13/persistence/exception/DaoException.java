package ac.at.tuwien.aic13.persistence.exception;

/**
 * The Class DaoException.
 * This exception is thrown on DAO failure situations.
 */
public class DaoException extends RuntimeException{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2106364589313095166L;
	
	/**
	 * Instantiates a new dao exception.
	 */
	public DaoException(){
		super("No reason given.");
	}
	
	/**
	 * Instantiates a new dao exception.
	 *
	 * @param cause the cause
	 */
	public DaoException(Throwable cause){
		super(cause);
	}
	
	/**
	 * Instantiates a new dao exception.
	 *
	 * @param message the message
	 */
	public DaoException(String message){
		super(message);
	}
	
	/**
	 * Instantiates a new dao exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DaoException(String message, Throwable cause){
		super(message, cause);
	}
}
