/**
 * 
 */
package ucergy.stage.mondrian;

/**
 * @author mkab
 *
 */
public class XmlFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mistake; 
	
	/**
	 * Default constructor
	 */
	public XmlFileException() {
		super();
		mistake = "unknown";
	}
	
	/**
	 * Second constructor with an error message as a parameter
	 * @param err - the error message
	 */
	public XmlFileException(String err) {
		super(err);
		mistake = err;
	}
	
	/**
	 * 
	 * @return the error committed
	 */
	public String getError() {
		return mistake;
	}

}
