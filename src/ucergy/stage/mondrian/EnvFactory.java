/**
 * 
 */
package ucergy.stage.mondrian;

/**
 * @author mkab
 * @version 1.0
 * 
 * This class determines Apache Tomcat's installation directory by checking
 * the environmental variables using the System property of Java.
 *  The variables checked are :
 *  
 *  catalina.home (properties)
 *  catalina.base (properties)
 *  CATALINA_HOME (environment)
 *  CATALINA_BASE (environment)
 * 
 */
public class EnvFactory {

	/**
	 * This methods runs a series of test to determine where Tomcat is installed.
	 * If it couldn't determine the installation folder a NullPointerException is thrown
	 * 
	 * @return a string containing Apache Tomcat's installation directory
	 * @throws NullPointerException
	 */
	public static String determineTomcatDir() throws NullPointerException {
		String str = "";

		if( System.getProperty("catalina.home") != null ) {
			str = System.getProperty("catalina.home");
			System.out.println("catalina home");
		}
		
		else if( System.getProperty("catalina.base") != null ) {
			str = System.getProperty("catalina.base");
			System.out.println("catalina.base");
		}
		else if( System.getenv("CATALINA_HOME") != null ) {
			str = System.getenv("CATALINA_HOME");
			System.out.println("CATALINA_HOME");
		}
		
		else if( System.getenv("CATALINA_HOME") != null ) {
			str = System.getenv("CATALINA_BASE");
			System.out.println("CATALINA_BASE");
		}
		
		else
			throw new NullPointerException("CATALINA_HOME and CATALINA_BASE are not set as environmental variables");
		
		return str;

	}



}
