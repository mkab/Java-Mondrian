package ucergy.stage.mondrian;

import java.io.File;
public class MondrianData {

	/** The host name of the server */
	private String hostname;

	/** The database's name */
	private String databaseName;

	/** The connection type*/
	private String connectionType;

	/** Contains the path of the XML file to load*/
	private String xmlFile;

	/** The port number*/
	private int portNumber;

	/** The user name for the given database*/
	private String username;

	/** The password for the user name*/
	private String password;

	/** The MDX query to be executed by Mondrian*/
	private String mdxQuery;


	/**
	 * 
	 * Default Constructor for the MondrianData
	 * 
	 * @param hostname 	       - the String containing the host's name
	 * @param databaseName 	   - the String containing the database's name
	 * @param connectionType   - the String containing the connection type (MYSQL or Oracle)
	 * @param xmlFile 	       - the String containing the path of the XML file
	 * @param portNumber       - an integer containing the port number for the connection
	 * @param username         - the String containing the user name of the database
	 * @param password         - the String containing the password for the user name
	 * @param mdxQuery         - the String containing the MDX query to be executed by mondrian. 
	 * This query would be written in the appropriate configuration file
	 * @throws XmlFileException - when the file isn't an XML file
	 */
	public MondrianData(String hostname, String databaseName,
			String connectionType, String xmlFile, int portNumber,
			String username, String password, String mdxQuery) throws XmlFileException {

		if(xmlFile.isEmpty() || !isXmlFile(xmlFile)) { 
			throw new XmlFileException("Veuillez choisir un fichier .xml");
		}

		this.hostname 		= hostname;
		this.databaseName	= databaseName;
		this.connectionType = connectionType;
		this.xmlFile 		= xmlFile;
		this.portNumber 	= portNumber;
		this.username 		= username;
		this.password 		= password;
		this.mdxQuery 		= mdxQuery;
	}

	/**
	 * 
	 * Second Constructor for the MondrianData
	 * 
	 * @param hostname 	     - the String containing the host's name
	 * @param databaseName 	 - the String containing the database's name
	 * @param connectionType - the String containing the database's type (MYSQL or Oracle)
	 * @param portNumber     - an integer containing the port number for the connection
	 * @param username       - the String containing the user name of the database
	 * @param password       - the String containing the password for the user name
	 * @param mdxQuery       - the String containing the MDX query to be executed by mondrian.
	 *  
	 * This query would be written in the appropriate configuration file
	 */
	public MondrianData(String hostname, String databaseName,
			String connectionType, int portNumber,
			String username, String password, String mdxQuery) {

		this.hostname = hostname;
		this.databaseName = databaseName;
		this.connectionType = connectionType;
		this.portNumber = portNumber;
		this.username = username;
		this.password = password;
		this.mdxQuery = mdxQuery;
	}

	/**
	 * 
	 * @param  - the xml file's path to validate
	 * @return - <b>true</b> if the file is an XML file, <b>false</b> otherwise.
	 */
	private boolean isXmlFile(String f) {

		File file = new File(f);

		if(file.isFile() && file.getName().endsWith("xml"))
			return true;

		return false;
	}


	/**
	 * @return the hostname - the host's name
	 */
	public String getHostname() {
		return hostname;
	}


	/**
	 * @return the database name - the database's name
	 */
	public String getDatabaseName() {
		return databaseName;
	}


	/**
	 * @return the connectionType - connection type
	 */
	public String getConnectionType() {
		return connectionType;
	}


	/**
	 * @return the the path of the XML file to load
	 */
	public String getXmlFile() {
		return xmlFile;
	}


	/**
	 * @return the portNumber - the port number
	 */
	public int getPortNumber() {
		return portNumber;
	}


	/**
	 * @return the username the user's name 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password - the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @return the mdxQuery - the mdx query
	 */
	public String getMdxQuery() {
		return mdxQuery;
	}


	/**
	 * @param hostname - the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}


	/**
	 * @param databaseName - the database name to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}


	/**
	 * @param connectionType - the database type to set
	 */
	public void setconnectionType(String connectionType) {
		this.connectionType = connectionType;
	}


	/**
	 * @param xmlFile - the path of the XML file to set
	 */
	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}


	/**
	 * @param portNumber the port number to set
	 */
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}


	/**
	 * @param username the user name to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @param mdxQuery the mdx query to set
	 */
	public void setMdxQuery(String mdxQuery) {
		this.mdxQuery = mdxQuery;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MondrianData [hostname=");
		builder.append(hostname);
		builder.append(", databaseName=");
		builder.append(databaseName);
		builder.append(", connectionType=");
		builder.append(connectionType);
		builder.append(", xmlFile=");
		builder.append(xmlFile);
		builder.append(", portNumber=");
		builder.append(portNumber);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", mdxQuery=");
		builder.append(mdxQuery);
		builder.append("]");
		return builder.toString();
	}


}
