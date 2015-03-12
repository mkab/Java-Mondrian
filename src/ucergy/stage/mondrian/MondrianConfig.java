/**
 * 
 */
package ucergy.stage.mondrian;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.util.IteratorIterable;


/**
 * @author mkab
 * @version 1.0
 */
public class MondrianConfig {

	//	private final static String thePath = "/webapps/mondrian/WEB-INF/queries/";
	//	private final static String xmlPath = "/webapps/mondrian/WEB-INF/";

	private MondrianData data;

	/** The JDBC Driver  */
	private String jdbcDriver;

	/**The JDBC URL*/
	private String jdbcUrl;

	/** String which contains the path of the xml file to be loaded by mondrian */
	private String catalogUri;


	/**
	 * Default constructor for MondrianConfig
	 * 
	 * @param jdbcDriver - String used to set the JDBC Driver 
	 * @param jdbcUrl    - String used to set the JDBC URL
	 * @param catalogUri - String which contains the path of the xml file to be loaded by mondrian
	 */
	public MondrianConfig(MondrianData _data) {

		this.data = _data;

		//determine jdbc driver
		this.determineJdbcDriver();

		//determine jdbc url
		this.determineJdbcUrl();

		if( _data.getXmlFile() != null ) {
			File f = new File(_data.getXmlFile());
			this.catalogUri = "/WEB-INF/queries/" + f.getName();
		}
	}



	/**
	 * Returns a string containing the correct format of the 
	 * JDBC Driver for different databases. <br />
	 * Exemple for the JDBC Driver for mysql would be : <br />
	 * <b>com.mysqql.jdbc.Driver</b>
	 * 
	 * @param data - of type MondrianData which contains all the necessary information for mondrian
	 */
	private void determineJdbcDriver(){

		if(data != null) {
			if(this.getData().getConnectionType().equalsIgnoreCase("mysql")) 
				this.setJdbcDriver("com.mysql.jdbc.Driver");
			else if(this.getData().getConnectionType().equalsIgnoreCase("oracle"))
				this.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
		}

	}

	/**
	 * Returns a string containing the correct format of the 
	 * JDBC URL for different databases.<br/>
	 * Exemple for the jdbc url for <b>mysql</b> with hostname <b>localhost</b> 
	 * on port <b>3306</b> and database <b>workbench</b> would look like this:<br/>
	 * <b>jdbc:mysql://localhost:3306/workbench</b>
	 * @param data - of type MondrianData which contains all the necessary information for mondrian
	 */
	private void determineJdbcUrl() {
		//		"?user=" + data.getUsername() + 
		//		"&password=" + data.getPassword()

		if(this.getData().getConnectionType().equalsIgnoreCase("mysql"))
			this.setJdbcUrl("jdbc:mysql://" + this.getData().getHostname() +
					"/" + this.getData().getDatabaseName());
		else if(this.getData().getConnectionType().equalsIgnoreCase("oracle"))
			this.setJdbcUrl("");
	}


	/**
	 * Copies the file from the origin path to the destination path
	 * @param origin
	 * @param destination
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void copyFile(String origin, String destination) throws FileNotFoundException, IOException {

		File f1 = new File(origin);
		File f2 = new File(destination + f1.getName());

		BufferedReader br = new BufferedReader(new FileReader(f1));
		BufferedWriter bw = new BufferedWriter(new FileWriter(f2));

		String line = null;

		while( (line = br.readLine()) != null ) {
			bw.write(line);
			bw.newLine();
		}

		br.close();
		bw.close();

		System.out.println("File " + f1.getName() + " copied to " +
				destination);

	}


	/**
	 * 
	 * @param root
	 * @param name
	 * @return
	 */
	private Element getElementByClassName(Element root, String name) {

		IteratorIterable<Content> it = root.getDescendants();

		while(it.hasNext()) { 
			Object current = it.next();
			if(current.getClass().getCanonicalName().equals("org.jdom2.Element")) {
				Element elt = (Element) current;
				if (elt.getName().equals(name)) {
					return elt;
				}
			}
		}

		return null;
	}


	/**
	 * This functions takes care of updating all the new mondrian properties
	 * in specific files. The files affected are: <br />
	 * <ul>
	 *    <li>arrows.jsp</li>
	 *    <li>colors.jsp</li>
	 *    <li>fourhier.jsp</li>
	 *    <li>mondrian.jsp</li>
	 *    <li>adhoc.jsp</li>
	 *    <li>datasources.xml</li>
	 * </ul>
	 * 
	 * @param path - String containing the path of Tomcat's installation directory
	 * @param data - of type MondrianData containing all the necessary information
	 * @throws IOException
	 */
	public void updateMondrianProperties( String path ) throws IOException {

		MondrianConfig.copyFile(this.getData().getXmlFile(), path);

		//list of files to change
		String[] files = {"arrows.jsp", "colors.jsp", "fourhier.jsp", "mondrian.jsp" };


		for(int i = 0; i < files.length; i++) {
			File tempFile = new File(path + files[i] + ".tmp");

			FileWriter fw     = new FileWriter(tempFile);
			BufferedWriter bw = new BufferedWriter(fw);

			FileReader fr     = new FileReader(path + files[i]);
			BufferedReader br = new BufferedReader(fr);

			String line;

			while( (line = br.readLine()) != null) {
				if(line.startsWith("<jp:mondrianQuery")) {
					bw.write("<jp:mondrianQuery id=\"query01\" " +
							"jdbcDriver=\"" + this.getJdbcDriver() + "\" " +
							"jdbcUrl=\"" + this.getJdbcUrl() + "\" " +
							"catalogUri=\"" + this.getCatalogUri() + "\" " +
							"jdbcUser=\"" + this.getData().getUsername() + "\" " +
							"jdbcPassword=\"" + this.getData().getPassword() + "\" " +
							"connectionPooling=\"false\">"
							);

					bw.newLine();
					bw.newLine();

					bw.write(this.getData().getMdxQuery());					

					bw.newLine();
					bw.newLine();

					bw.write("</jp:mondrianQuery>");

					bw.newLine();
					bw.newLine();

					if(files[i].equals("arrows.jsp")) {
						bw.write("<c:set var=\"title01\" scope=\"session\">Arrows</c:set>");
					}

					else if(files[i].equals("colors.jsp")) {
						bw.write("<c:set var=\"title01\" scope=\"session\">Colors</c:set>");
					}

					else if(files[i].equals("fourhier.jsp")) {
						bw.write("<c:set var=\"title01\" scope=\"session\">4 hierarchies on one axis</c:set>");
					}

					else if(files[i].equals("mondrian.jsp")) {
						bw.write("<c:set var=\"title01\" scope=\"session\">Test Query uses Mondrian OLAP</c:set>");
					}

					break;
				}

				bw.write(line);
				bw.newLine();

			}

			bw.close();
		}
	}

	public void updateXmlProperties(String path) throws JDOMException, IOException {

		System.out.println("Entering xml properties");
		String input = "Provider=mondrian;" +
				"Jdbc=" + this.getJdbcUrl() + ";" +
				"JdbcUser=" + this.getData().getUsername() + ";" +
				"JdbcPassword=" + this.getData().getPassword() + ";" +
				"JdbcDrivers=" + this.getJdbcDriver() + ";" +
				"Catalog="+ this.getCatalogUri()+ ";";

		SAXBuilder builder = new SAXBuilder();

		File xmlFile = new File(path + "datasources.xml");

		Document doc = (Document) builder.build(xmlFile);
		Element rootNode = doc.getRootElement(); //get root element

		//update DataSourceInfo attribute 
		Element datasource = this.getElementByClassName(rootNode, "DataSourceInfo");
		//System.out.println(datasource.getText());

		datasource.setText(input);
		//System.out.println(datasource.getText());

		XMLOutputter xmlOutput = new XMLOutputter();

		//write to the xml file
		xmlOutput.setFormat(Format.getPrettyFormat());

		xmlOutput.output(doc, new FileWriter(path + "datasources.xml.tmp"));

		System.out.println("File updated!");

	}


	/**
	 * 
	 * @param data
	 * @return boolean - true if connection exist, false otherwise
	 * @throws ClassNotFoundException
	 * @throws SQLException

	public boolean readDatabase() throws ClassNotFoundException, SQLException {
		boolean bool = false;

		Connection connect = null;

		//Load the required database Driver
		Class.forName(this.getJdbcDriver());

		connect = DriverManager.getConnection(this.getJdbcUrl(), this.getData().getUsername(),
				this.getData().getPassword());

		if (connect != null) {
			bool = true;
			connect.close();
		}

		System.out.println("bool = " + bool + " db = " + connect.getMetaData().getURL());
		return bool;

	}
	 */



	/**
	 * @return the jdbcDriver
	 */
	public String getJdbcDriver() {
		return jdbcDriver;
	}

	/**
	 * @return the jdbcUrl
	 */
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	/**
	 * @return the catalogUri
	 */
	public String getCatalogUri() {
		return catalogUri;
	}

	/**
	 * @param jdbcDriver - the jdbc Driver to set
	 */
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	/**
	 * @param jdbcUrl - the jdbc Url to set
	 */
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	/**
	 * @param catalogUri - the catalog Uri to set
	 */
	public void setCatalogUri(String catalogUri) {
		this.catalogUri = catalogUri;
	}

	/**
	 * @return the data
	 */
	public MondrianData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(MondrianData data) {
		this.data = data;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MondrianConfig [data=");
		builder.append(data);
		builder.append(", jdbcDriver=");
		builder.append(jdbcDriver);
		builder.append(", jdbcUrl=");
		builder.append(jdbcUrl);
		builder.append(", catalogUri=");
		builder.append(catalogUri);
		builder.append("]");
		return builder.toString();
	}
}

//public  void testsplit() throws IOException {
//
//
//	String line = "<jp:mondrianQuery id=\"query01\"> some text " +
//			"</jp:mondrianQuery>";
//
//	String[] str  = line.split("(?:^|>)[^<]*(?:<|$)");
//	for(int i = 0; i < str.length; i++)
//		System.out.println("str[" + i + "] = " +str[i]);
//
//}